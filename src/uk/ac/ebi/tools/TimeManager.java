/*
 * Copyright (C) 2013 Vishal Siramshetty <vishal[at]ebi.ac.uk>
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
package uk.ac.ebi.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author Vishal Siramshetty <vishal[at]ebi.ac.uk>
 */
public class TimeManager {

    public String timeUnit;
    public final String hours = "hours";
    public final String minutes = "minutes";
    public final String seconds = "seconds";

    public double startTime;
    public SimpleDateFormat dateFormat;

    /**
     * Returns the time taken for a process when provided a start time and the time
     * unit (seconds, minutes or hours).
     *
     * @param startTime
     * @param timeUnit
     * @return
     */
    public double getTimeElapsed(double startTime, String timeUnit) {
        this.dateFormat = new SimpleDateFormat("HH:MM:SS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        this.startTime = System.currentTimeMillis();
        this.timeUnit = timeUnit;
        this.startTime = startTime;

        return getElapsedTime(timeUnit);
    }

    private double getElapsedTime(String timeUnit) {
        double currentTime = System.currentTimeMillis();
        switch (timeUnit) {
            case hours:
                return (currentTime - startTime) / (60 * 60 * 1000);
            case minutes:
                return (currentTime - startTime) / (60 * 1000);
            case seconds:
                return (currentTime - startTime) / 1000;
        }
        return 0;

    }

}
