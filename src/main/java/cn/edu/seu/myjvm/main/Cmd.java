package cn.edu.seu.myjvm.main;

import org.apache.commons.cli.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by a on 2018/2/27.
 */
public class Cmd {
    private boolean helpFlag = false;
    private boolean versionFlag = false;
    private boolean verboseClassFlag = false;
    private boolean verboseInstFlag = false;
    private String cpOption;
    private String XjreOption;
    private String clazz;
    private List<String> args;

    public static Cmd parseCmd(String[] args) throws ParseException {
        Cmd cmd = new Cmd();

        Options options = new Options();
        options.addOption("h", false, "show help message");
        options.addOption("v", false, "show version message");
        options.addOption("verbose", false, "enable verbose output");
        options.addOption("vClass", false, "enable verbose output");
        options.addOption("vInst", false, "enable verbose output");
        options.addOption("cp", true, "classpath");
        options.addOption("classpath", true, "classpath");
        options.addOption("Xjre", true, "path to jre");
        options.addOption("class", true, "this.class");

        CommandLineParser commandLineParser = new PosixParser();
        CommandLine commandLine = commandLineParser.parse(options, args);

        if (commandLine.hasOption("h"))
            cmd.setHelpFlag(true);
        if (commandLine.hasOption("v"))
            cmd.setVersionFlag(true);
        if (commandLine.hasOption("verbose"))
            cmd.setVerboseClassFlag(true);
        if (commandLine.hasOption("vClass"))
            cmd.setVerboseClassFlag(true);
        if (commandLine.hasOption("vInst"))
            cmd.setVerboseInstFlag(true);
        if (commandLine.hasOption("cp"))
            cmd.setCpOption(commandLine.getOptionValue("cp"));
        if (commandLine.hasOption("Xjre"))
            cmd.setXjreOption(commandLine.getOptionValue("Xjre"));

        if (args.length > 0) {
            cmd.setClazz(commandLine.getOptionValue("class"));
            int index = 0;
            for (int i = 0; i < args.length; i++)
                if (args[i].equals("-class"))
                    index = i;
            cmd.setArgs(Arrays.asList(args).subList(index + 2, args.length));
        }

        return cmd;
    }

    public static void printUsage() {
        System.out.println("Usage: %s [-options] class [args...]");
    }

    public boolean isHelpFlag() {
        return helpFlag;
    }

    public void setHelpFlag(boolean helpFlag) {
        this.helpFlag = helpFlag;
    }

    public boolean isVersionFlag() {
        return versionFlag;
    }

    public void setVersionFlag(boolean versionFlag) {
        this.versionFlag = versionFlag;
    }

    public String getCpOption() {
        return cpOption;
    }

    public void setCpOption(String cpOption) {
        this.cpOption = cpOption;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public String getXjreOption() {
        return XjreOption;
    }

    public void setXjreOption(String xjreOption) {
        XjreOption = xjreOption;
    }

    public boolean isVerboseClassFlag() {
        return verboseClassFlag;
    }

    public void setVerboseClassFlag(boolean verboseClassFlag) {
        this.verboseClassFlag = verboseClassFlag;
    }

    public boolean isVerboseInstFlag() {
        return verboseInstFlag;
    }

    public void setVerboseInstFlag(boolean verboseInstFlag) {
        this.verboseInstFlag = verboseInstFlag;
    }
}
