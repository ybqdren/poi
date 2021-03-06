/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */
package org.apache.poi.ss.excelant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.poi.ss.excelant.util.ExcelAntWorkbookUtil;
import org.apache.poi.ss.excelant.util.ExcelAntWorkbookUtilFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestExcelAntSet {


    // This is abstract in nature, so we'll use a
    // concrete instance to test the set methods.
    private ExcelAntSet fixture ;

    private static final String mortgageCalculatorFileName =
        TestBuildFile.getDataDir() + "/spreadsheet/mortgage-calculation.xls" ;

    @BeforeEach
    void setUp() {
        fixture = new ExcelAntSetDoubleCell() ;
    }

    @AfterEach
    void tearDown() {
        fixture = null ;
    }

    @Test
    void testSetter() {
        String cell = "simpleCellRef!$F$1" ;

        fixture.setCell( cell ) ;

        String cellStr = fixture.getCell() ;

        assertNotNull( cellStr ) ;
        assertEquals( cell, cellStr ) ;
    }

    @Test
    void testSetWorkbookUtil() {
        ExcelAntWorkbookUtil util = ExcelAntWorkbookUtilFactory.getInstance(
                                                 mortgageCalculatorFileName ) ;

        assertNotNull( util ) ;

        fixture.setWorkbookUtil( util ) ;
    }
}
