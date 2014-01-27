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

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author tckb < Chandra [dot] Tungathurthi [at] rwth-aachen.de >
 */
public class GenericCmdOutputObserver implements Observer {

    private String rawOutput = "";
    private boolean outNotAvailable = true;

    @Override
    public void update(Observable o, Object arg) {

        if (arg instanceof StringBuilder) {
            rawOutput = ((StringBuilder) arg).toString();
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

        return rawOutput;
    }

}
