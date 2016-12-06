package com.company;

/**
 * Created by MasterWillis on 06/12/2016.
 */
public class UserInputFactory {

    RecursiveLsys lsys;
    public enum InputType {LEAPLISTENER, KEYLISTENER};

    public UserInputFactory(RecursiveLsys lsys) {
        this.lsys = lsys;
    }

    public UserInput fetchUserInput(InputType inputType) {

        switch (inputType) {
            case LEAPLISTENER:
                System.out.println("Leap er nice nu");
                break;

            case KEYLISTENER:
                System.out.println("Jeg har sgu fundet mig en keylistener");
                return new ExpandKeyListener(lsys);

            default:
                System.out.println("I cannot detect which inputtype, you want. chose again");
        }
        return null;
    }
}