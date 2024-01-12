/*
 * This file is part of SympleGit
 * SympleGit: Straightforward  Git in Java. Follows 
 *           'AI-Extensible Open Source Software' pattern
 * Copyright (C) 2024,  KawanSoft SAS
 * (http://www.kawansoft.com). All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.symplegit.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Formated dtaes as trings for API classes.
 *
 * @author Nicolas de Pomereu
 *
 */
public class ApiDateUtil {


    public static String getDateWithTime() {
	return getDateReverse() + "-" + getHourMinute();
    }
    
    /**
     * Get the formated date as "yyyy-mm-dd"
     *
     * @return the date as "yyyy-mm-dd"
     */
    public static String getDateReverse() {
        String sDate;

        int nDay = new GregorianCalendar().get(Calendar.DAY_OF_MONTH);
        int nMonth = new GregorianCalendar().get(Calendar.MONTH) + 1;

        sDate = "" + new GregorianCalendar().get(Calendar.YEAR);

        if (nMonth < 10) {
            sDate += "-0" + nMonth;
        } else {
            sDate += "-" + nMonth;
        }

        if (nDay < 10) {
            sDate += "-0" + nDay;
        } else {
            sDate += "-" + nDay;
        }

        return sDate;
    }
    
    public static String getHourMinute() {
	GregorianCalendar gc = new GregorianCalendar();

	int nHours = gc.get(Calendar.HOUR_OF_DAY);
	int nMinutes = gc.get(Calendar.MINUTE);
	
	return formatHour(nHours, nMinutes);
    }
    
    /**
     * This method formats a String in hh/mm
     * 
     * @param hour
     * @param min
     * @return the formatted String
     */
    private static String formatHour(int hour, int min) {
	String sHour = "";
	if (hour < 10) {
	    sHour += "0" + hour;
	} else {
	    sHour += hour;
	}
	if (min < 10) {
	    sHour += "0" + min;
	} else {
	    sHour += min;
	}

	return sHour;
    }
}
