package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GestionExceptionTest {


    @Test
/*    @Disabled*/
    public void exceptionTest() {
        String input = "";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputReaderUtil inputexception = new InputReaderUtil();
        assertThrows(NoSuchElementException.class, inputexception::readVehicleRegistrationNumber);
    }

}
