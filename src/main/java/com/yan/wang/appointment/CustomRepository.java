package com.yan.wang.appointment;

import java.util.List;

public interface CustomRepository {
    public List<Appointment> findAllByEmail(String email);
}