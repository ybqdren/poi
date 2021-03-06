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

package org.apache.poi.hssf.record.pivot;

import static org.apache.poi.hssf.record.TestcaseRecordInputStream.confirmRecordEncoding;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.apache.poi.hssf.record.RecordInputStream;
import org.apache.poi.hssf.record.TestcaseRecordInputStream;
import org.apache.poi.hssf.record.pivottable.PageItemRecord;
import org.apache.poi.util.HexRead;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PageItemRecord}
 */
final class TestPageItemRecord {
    @Test
    void testMoreThanOneInfoItem_bug46917() {
        byte[] data = HexRead.readFromString("01 02 03 04 05 06 07 08 09 0A 0B 0C");
        RecordInputStream in = TestcaseRecordInputStream.create(PageItemRecord.sid, data);
        PageItemRecord rec = new PageItemRecord(in);
        assertNotEquals(6, in.remaining(), "Identified bug 46917");
        assertEquals(0, in.remaining());

        assertEquals(4+data.length, rec.getRecordSize());
    }

    @Test
    void testSerialize() {
        confirmSerialize("01 02 03 04 05 06");
        confirmSerialize("01 02 03 04 05 06 07 08 09 0A 0B 0C");
        confirmSerialize("01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F 10 11 12");
    }

    private static void confirmSerialize(String hexDump) {
        byte[] data = HexRead.readFromString(hexDump);
        RecordInputStream in = TestcaseRecordInputStream.create(PageItemRecord.sid, data);
        PageItemRecord rec = new PageItemRecord(in);
        assertEquals(0, in.remaining());
        assertEquals(4+data.length, rec.getRecordSize());
        byte[] data2 = rec.serialize();
        confirmRecordEncoding(PageItemRecord.sid, data, data2);
    }
}
