package com.qumasi.execution;

import com.qumasi.controller.RegisterController;
import com.qumasi.view.RegisterView;

public class execute {
    public static void main(String[] args) {
        execute execute = new execute();
        RegisterView rv=new RegisterView();
        RegisterController rc=new RegisterController(rv);
    }
}
