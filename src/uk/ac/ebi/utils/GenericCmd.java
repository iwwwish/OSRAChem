/*
 * Copyright (C) 2013 tckb < Chandra [dot] Tungathurthi [at] rwth-aachen.de >
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package uk.ac.ebi.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tckb
 */
public class GenericCmd implements Runnable {

    protected String name;
    protected String logText = null;
    protected GenericCmdLog myLog = null;
    protected GenericCmdLogObserver myObserver = null;
    protected String cmd = null;
    protected boolean runBatch = false;
    protected ArrayList<String> flagList = new ArrayList<String>();
    private HashMap<Character, String> inputs = null;
    private java.lang.Character OFmt = null;
    private String OFname = null;

    public GenericCmd() {
        this("command");
    }

    public GenericCmd(String name) {
        this.setName(name);
        inputs = new HashMap<Character, String>();
        myLog = new GenericCmdLog(getName());//this.getName() + "-log");
    }

    /**
     * Run the command
     *
     * @param runBatch true : if the command has to be executed as batch outside
     * from the java environment<br/> false: with in the environment
     */
    public void runCommand(boolean runBatch) {

        this.runBatch = runBatch;
        new Thread(this).start();

    }

    protected void runNormal() {
        myLog.writeln("==============================================");
        try {
            String tmp;

            // Add  main command
            flagList.add(cmd);

            if (!inputs.isEmpty() && OFmt != null && OFname != null) {

                // Add Inputs
                for (Entry e : inputs.entrySet()) {
                    flagList.add("-" + e.getKey());
                    flagList.add((String) e.getValue());

                }
                // Add Ouputs
                flagList.add("-" + this.OFmt);
                flagList.add(this.OFname);
            }

            myLog.writeln("Executing command: " + flagList);
            ProcessBuilder processb = new ProcessBuilder(flagList);

            // merge error & output 
            processb.redirectErrorStream(true);
            Process cmdProcess = processb.start();

            myLog.writeln("Output:");
            BufferedReader br = new BufferedReader(new InputStreamReader(cmdProcess.getInputStream()));

            while ((tmp = br.readLine()) != null) {
                myLog.writeln(tmp);
            }

            int returnVal = cmdProcess.waitFor();
            myLog.writeln("Command executed with return value: " + returnVal);
            myLog.writeln("==============================================");

        } catch (Exception ex) {
            myLog.writeln("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {

            myLog.done();
        }
    }

    protected void runCommandAsBatch() {
        String flags = " "; // deprecated! remove it asap
        String tmp;
        myLog.writeln("==============================================");
        try {
            File tmpLogFile = Utility.createTmpFile(this.getName(), ".tmp");
            File tmpBatchFile = Utility.createTmpFile(this.getName(), ".bat");
            tmpBatchFile.setExecutable(true);

            // prepare command lines
            // ArrayList<String> flagList = new ArrayList<String>();
            flagList.add(cmd);
            if (!inputs.isEmpty() && OFmt != null && OFname != null) {

                // Inputs
                for (Entry e : inputs.entrySet()) {
                    flags += "-" + e.getKey() + " " + e.getValue() + " ";

                    flagList.add("-" + e.getKey());
                    flagList.add((String) e.getValue());

                }
                // outputs
                flags += "-" + this.OFmt + " " + this.OFname + " ";
                flagList.add("-" + this.OFmt);
                flagList.add(this.OFname);
            }
            flagList.add(" >> " + tmpLogFile.getPath());
            String str = "";
            for (String s : flagList) {
                str = str + " " + s;
            }

            // Load commandlines to the batchFile
            Utility.saveStringToFile(str, tmpBatchFile);

            // Execute the batchFile
            myLog.writeln("Executing command: " + cmd + flags);
            myLog.writeln(str);

            //   Process cmdProcess = Runtime.getRuntime().exec(new String[]{"cmd", "/c", tmpBatchFile.getAbsolutePath()});
            Process cmdProcess = Runtime.getRuntime().exec(tmpBatchFile.getAbsolutePath());

            System.out.println("Executing this command: " + tmpBatchFile.getAbsolutePath());

            BufferedReader br = new BufferedReader(new InputStreamReader(cmdProcess.getErrorStream()));
            while ((tmp = br.readLine()) != null) // logText += br.readLine();
            {
                myLog.writeln(tmp);

            }

            System.out.println("Output:");
            br = new BufferedReader(new InputStreamReader(cmdProcess.getInputStream()));

            while ((tmp = br.readLine()) != null) // logText += br.readLine();
            {
                myLog.writeln(tmp);
            }

            // Now load the tmpLogFile to the log file
            myLog.loadFromFile(tmpLogFile);

            int returnVal = cmdProcess.waitFor();
            myLog.writeln("Command executed with return value: " + returnVal);
            myLog.writeln("==============================================");

            //  System.out.println("Command executed succesfully: " + cmdProcess.waitFor());
        } catch (IOException ex) {
            myLog.writeln("Error: " + ex.getMessage());
        } catch (InterruptedException ex) {
            myLog.writeln("Error: " + ex.getMessage());
        } finally {

            myLog.done();
        }

    }

    public void setOutput(java.lang.Character fmt, String fname) {
        this.OFmt = fmt;
        this.OFname = fname;
    }

    public void setInputs(HashMap<java.lang.Character, String> inputs) {

        this.inputs = inputs;
    }

    public void setCommand(String pathtoCommand) {
        this.cmd = pathtoCommand;
    }

    // Just an idea may not be impl
    public String lineByLineOutput() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void run() {
        if (runBatch) {
            runCommandAsBatch();
        } else {
            runNormal();
        }
    }

    public GenericCmdLog getLogger() {
        return this.myLog;
    }

    public String getOuputLog() throws Exception {

        if (logText != null) {
            return logText;
        } else {
            throw new Exception("Log not available! Did the command run?");
        }
    }

    @Override
    public String toString() {
        return "[" + this.getName() + ":" + flagList + "]";

    }

    private void setName(String name) {
        this.name = name;
    }

    public final String getName() {
        return this.name;
    }

    public void attachLogObserver(GenericCmdLogObserver testPMObserver) {
        myLog.addObserver(testPMObserver);

    }

    public GenericCmdLogObserver enableDefaultObserver() {
        try {
            myObserver = new GenericCmdLogObserver();
            myObserver.attachOut(System.out);
            this.attachLogObserver(myObserver);
        } catch (Exception ex) {
            Logger.getLogger(GenericCmd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myObserver;
    }

    public class GenericCmdLog extends Observable {

        protected String text = "";
        protected String name;
        protected boolean isTextReady = false;

        public GenericCmdLog(String name) {
            this.name = name + "-log";
            writeln("LogStarted: " + Calendar.getInstance().getTime());
        }

        public final void writeln(String log) {
            text += "\n[" + this.name + "]" + log;
        }

        public String getLog() {
            return text;

        }

        public void done() {
            setChanged();
            notifyObservers(this.text);

        }

        public String getName() {
            return this.name;
        }

        protected void loadFromFile(File tmpLog) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(tmpLog));

                String str;
                while ((str = br.readLine()) != null) {
                    this.writeln(str);
                }

            } catch (Exception ex) {
                Logger.getLogger(GenericCmd.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
