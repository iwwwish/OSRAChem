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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

/**
 *
 * @author tckb
 */
public class GenericCmdLogObserver implements Observer {

    // List of out's 
    private PrintStream outStream;
    private JTextComponent outTxComp;
    private JTextArea outTxArea;
    private String out = null;
    private boolean uiNotify;
    // --Deprecated block--
    private final ArrayList<String> old_classNames = new ArrayList<String>();
    private final ArrayList<String> old_methodNames = new ArrayList<String>();
    private Object callBackObject = null;
    // --Deprecated block--
    private final LinkedList<String> classNames = new LinkedList<String>();
    private final LinkedList<String> methodNames = new LinkedList<String>();
    private final LinkedList<Object[]> callBackMethodParams = new LinkedList<Object[]>();
    private boolean outNotAvailable = true;

    /**
     * At least one out should be attached to receive UI notification An output
     * can be an instance of PrintStream, JTextArea or jTextComponent<br/>
     *
     * @see java.io.PrintStream
     * @see JTextArea
     * @see JTextComponent
     * @param obj
     * @throws Exception
     */
    public void attachOut(Object obj) throws Exception {
        if (obj instanceof PrintStream) {
            this.outStream = ((PrintStream) obj);
        } else if (obj instanceof JTextArea) {
            this.outTxArea = ((JTextArea) obj);
        } else if (obj instanceof JTextComponent) {
            this.outTxComp = ((JTextComponent) obj);
        } else {
            //throw new Exception(this.getClass().getName() + ": Unknown Out, Cannot attach");
            System.err.println(obj.getClass() + ": Unknown Out! Cannot attach.");
        }

    }

    @Override
    public void update(Observable o, Object arg) {

        // Saves the log to the out's
        if (arg instanceof String) {

            
            
            
            
            
            String text = (String) arg;

            if (outStream != null) {
                outStream.println(text);
            }
            if (outTxComp != null) {
                outTxComp.setText(text);
            }
            if (outTxArea != null) {
                outTxArea.append(text);
            }

            out = text;

            if (uiNotify) {

                if (outStream != null) {
                    JOptionPane.showMessageDialog(null, "Process completed!", ((GenericCmd.GenericCmdLog) o).getName(), JOptionPane.INFORMATION_MESSAGE);
                }
                if (outTxComp != null) {
                    JOptionPane.showMessageDialog(outTxComp, " Process completed!", ((GenericCmd.GenericCmdLog) o).getName(), JOptionPane.INFORMATION_MESSAGE);
                }
                if (outTxArea != null) {

                    JOptionPane.showMessageDialog(outTxArea, " Process completed!", ((GenericCmd.GenericCmdLog) o).getName(), JOptionPane.INFORMATION_MESSAGE);
                }

            }

            while (!classNames.isEmpty()) {

                String className = classNames.poll();
                String mName = methodNames.poll();
                Object[] currCallBackMethodParams = callBackMethodParams.poll();

                try {
                    Object objInst = Class.forName(className).newInstance();

                    Class cbMClass[] = new Class[currCallBackMethodParams.length];
                    for (int i = 0; i < currCallBackMethodParams.length; i++) {
                        cbMClass[i] = currCallBackMethodParams[i].getClass();
                    }

                    Class.forName(className).getMethod(mName, cbMClass).invoke(objInst, currCallBackMethodParams);

                } catch (Exception ex) {

                    System.out.println("Exception in class: " + className + ":" + ex.getMessage());

                }

            }

            // --- deprecated block       
            // Generic class-Method calls
            if (callBackObject != null) {
                for (String clas : old_classNames) {
                    try {
//                        Object objInst = Class.forName(clas).newInstance();
                        String mName = old_methodNames.get(old_classNames.indexOf(clas));

                        Class.forName(clas).getMethod(mName).invoke(callBackObject);

                    } catch (Exception ex) {

                        System.out.println("Exception in class: " + clas + ":" + ex.getLocalizedMessage());
                        ex.printStackTrace();

                    }
                }
            } else {
                for (String clas : old_classNames) {
                    try {
                        Object objInst = Class.forName(clas).newInstance();
                        String mName = old_methodNames.get(old_classNames.indexOf(clas));
                        Class.forName(clas).getMethod(mName).invoke(objInst);

                    } catch (Exception ex) {

                        System.out.println("Exception in class: " + clas + ":" + ex.getLocalizedMessage());

                    }
                }
            }

            // --- deprecated block       
            outNotAvailable = false;
        }

    }

    /**
     * Blocks the current thread until the output is available returns the log
     * as string
     *
     * @return output log
     * @throws InterruptedException
     */
    public String waitForOutput() throws InterruptedException {
        while (outNotAvailable) {

            Thread.sleep(100);
        }

        return out;
    }

    /**
     * Enable UI notification whenever there is an update
     */
    public void enableUINotification() {
        this.uiNotify = true;
    }

    //TODO: this can be extended to support method calls to different class
    /**
     * Attached method is invoked when ever an update is received.<br/> NOTE:
     * Methods attached using 'attachMethodCallWithParams' supersedes the
     * methods attached using this call in the execution order.
     *
     * @deprecated Use attachMethodCallWithParams
     * @see #attachMethodCallWithParams(java.lang.String, java.lang.String,
     * java.lang.Object[])
     *
     * @param className the corresponding clas name
     * @param methodName the method which supposed to be called
     */
    public void attachMethodCall(String className, String methodName) {
        old_classNames.add(className);
        old_methodNames.add(methodName);
    }

    /**
     * Attached method is invoked when ever an update is received.<br/> The
     * method <i> methodName </i> is invoked by creating a newInstance of
     * <i>className</i><br/><br/> NOTE:<br/> Only methods of type <i> public
     * void methodName(Object... params){ ... } </i> are supported</br>
     *
     * @param className
     * @param methodName
     * @param params Optional parameters to the method
     */
    public void attachMethodCallWithParams(String className, String methodName, Object... params) {
        classNames.add(className);
        methodNames.add(methodName);
        callBackMethodParams.add(params);
    }

    /**
     * Optional method, if an explicit object is not attached then, the call
     * would be done by creating a new instance of the class given by the
     * attachMethodCall. Once a call back object has been attached then the
     * following method calls <b>must</b> belong to the class that the object
     * belongs to.
     *
     * @deprecated Only applicable to 'attachMethodCall'
     * @param object
     */
    public void attachCallBackObject(Object object) {
        this.callBackObject = object;
    }
}
