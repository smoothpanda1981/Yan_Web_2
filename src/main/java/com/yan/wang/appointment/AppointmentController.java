package com.yan.wang.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class AppointmentController {

    int count=0;

    @Autowired
    PersonRepository personRepo;

    @Autowired
    DoctorRepository docRepo;

    @Autowired
    AppointmentRepository appRepo;

    @GetMapping("/appointment/register")
    public String register() {
        return "appointment/register";
    }

    @GetMapping("/appointment/registerdoc")
    public String registerdoc() {
        return "appointment/registerdoc";
    }

    @GetMapping("/appointment")
    public String home() {
        return "appointment/start";
    }

    @GetMapping("/appointment/patlog")
    public String patlog() {
        return "appointment/index";
    }

    @GetMapping("/appointment/doclog")
    public String doclog() {
        return "appointment/doclog";
    }

    @PostMapping("/appointment/registered")
    public String registered(Person person) {
        personRepo.save(person);
        return "redirect:/appointment";
    }

    @PostMapping("/appointment/registereddoc")
    public String registereddoc(Doctor doctor) {
        docRepo.save(doctor);
        return "redirect:/appointment";
    }

    @GetMapping("/appointment/fail_login")
    public String fail_login() {
        return "redirect:/appointment/fail_login";
    }

    @PostMapping("/appointment/authenticate")
    public String authenticate(Person person, HttpSession session) {
        if(personRepo.exists(person.getEmail()) && personRepo.findOne(person.getEmail()).getPassword().equals(person.getPassword())) {
            session.setAttribute("person", person.getEmail());
            return "redirect:/appointment/home";
        }
        return "redirect:/appointment/fail_login";
    }

    @PostMapping("/appointment/authenticatedoc")
    public String authenticatedoc(Doctor doctor,HttpSession session) {
        if(docRepo.exists(doctor.getEmail()) && docRepo.findOne(doctor.getEmail()).getPassword().equals(doctor.getPassword())) {
            session.setAttribute("doctor", doctor.getEmail());
            return "redirect:/appointment/patientlist";
        }
        return "redirect:/appointment/fail_login";
    }

    @PostMapping("/appointment/cancel")
    public String cancel(AppointmentDelete dApp) {
        appRepo.delete(dApp.getAppId());
        return "redirect:/appointment/userdetails";
    }

    @GetMapping("/appointment/home")
    public ModelAndView display(HttpSession session) {
        ModelAndView mav = new ModelAndView("appointment/fail_login");
        String email = null;


        if(session.getAttribute("person")!=null) {
            mav = new ModelAndView("appointment/home");
            email = (String) session.getAttribute("person");
        }

        mav.addObject("email",email);

        return mav;


    }

    @PostMapping("/appointment/assignment")
    public String submitted(Appointment app) {
        app.setAppId(count++);
        app.setStatus("Active");
        appRepo.save(app);

        return "redirect:/appointment/docdetails";
    }

    @GetMapping("/appointment/docdetails")
    public ModelAndView DocDetails(HttpSession session) {

        List<Doctor> doctors = new ArrayList<Doctor>();
        docRepo.findAll().forEach(doctors::add);
        Map<String, Object> params = new HashMap<>();

        params.put("doctor", doctors);
        params.put("email", session.getAttribute("person"));

        return new ModelAndView("appointment/doctorlist", params);
    }

    @GetMapping("/appointment/userdetails")
    public ModelAndView UserDetails(HttpSession session) {
        List<Appointment> apps = appRepo.findAllByEmail(session.getAttribute("person").toString());
        Map<String,Object> params = new HashMap<>();

        params.put("appointments", apps);
        params.put("email", session.getAttribute("person"));

        return new ModelAndView("appointment/appointed",params);
    }
    @GetMapping("/appointment/patientlist")
    public ModelAndView PatientList(HttpSession session) {
        List<Appointment> apps = appRepo.findByDocId(session.getAttribute("doctor").toString());
        Map<String,Object> params = new HashMap<>();

        params.put("appointments", apps);
        params.put("email", session.getAttribute("doctor"));

        return new ModelAndView("appointment/appointedDoc",params);
    }
}
