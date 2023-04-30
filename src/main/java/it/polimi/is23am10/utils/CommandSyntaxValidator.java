package it.polimi.is23am10.utils;

import org.apache.logging.log4j.core.tools.picocli.CommandLine.Command;

public class CommandSyntaxValidator {
    public CommandSyntaxValidator(){
    }

    public static boolean validate (String s){
        if(s!=null){
            return true;
        }
        return false;
    }
}
