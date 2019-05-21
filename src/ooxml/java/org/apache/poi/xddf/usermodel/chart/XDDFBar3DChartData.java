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

package org.apache.poi.xddf.usermodel.chart;

import java.util.Map;

import org.apache.poi.util.Beta;
import org.apache.poi.util.Internal;
import org.apache.poi.xddf.usermodel.XDDFShapeProperties;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTAxDataSource;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBar3DChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBarSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTNumDataSource;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTSerTx;

@Beta
public class XDDFBar3DChartData extends XDDFChartData {
    private CTBar3DChart chart;

    public XDDFBar3DChartData(CTBar3DChart chart, Map<Long, XDDFChartAxis> categories,
            Map<Long, XDDFValueAxis> values) {
        this.chart = chart;
        if (chart.getBarDir() == null) {
            chart.addNewBarDir().setVal(BarDirection.BAR.underlying);
        }
        for (CTBarSer series : chart.getSerList()) {
            this.series.add(new Series(series, series.getCat(), series.getVal()));
        }
        defineAxes(categories, values);
    }

    private void defineAxes(Map<Long, XDDFChartAxis> categories, Map<Long, XDDFValueAxis> values) {
        if (chart.sizeOfAxIdArray() == 0) {
            for (Long id : categories.keySet()) {
                chart.addNewAxId().setVal(id);
            }
            for (Long id : values.keySet()) {
                chart.addNewAxId().setVal(id);
            }
        }
        defineAxes(chart.getAxIdArray(), categories, values);
    }

    @Internal
    @Override
    protected void removeCTSeries(int n) {
        chart.removeSer(n);
    }

    @Override
    public void setVaryColors(Boolean varyColors) {
        if (varyColors == null) {
            if (chart.isSetVaryColors()) {
                chart.unsetVaryColors();
            }
        } else {
            if (chart.isSetVaryColors()) {
                chart.getVaryColors().setVal(varyColors);
            } else {
                chart.addNewVaryColors().setVal(varyColors);
            }
        }
    }

    public BarDirection getBarDirection() {
        return BarDirection.valueOf(chart.getBarDir().getVal());
    }

    public void setBarDirection(BarDirection direction) {
        chart.getBarDir().setVal(direction.underlying);
    }

    public BarGrouping getBarGrouping() {
        if (chart.isSetGrouping()) {
            return BarGrouping.valueOf(chart.getGrouping().getVal());
        } else {
            return null;
        }
    }

    public void setBarGrouping(BarGrouping grouping) {
        if (grouping == null) {
            if (chart.isSetGrouping()) {
                chart.unsetGrouping();
            }
        } else {
            if (chart.isSetGrouping()) {
                chart.getGrouping().setVal(grouping.underlying);
            } else {
                chart.addNewGrouping().setVal(grouping.underlying);
            }
        }
    }

    public Integer getGapDepth() {
        if (chart.isSetGapDepth()) {
            return chart.getGapDepth().getVal();
        } else {
            return null;
        }
    }

    public void setGapDepth(Integer depth) {
        if (depth == null) {
            if (chart.isSetGapDepth()) {
                chart.unsetGapDepth();
            }
        } else {
            if (chart.isSetGapDepth()) {
                chart.getGapDepth().setVal(depth);
            } else {
                chart.addNewGapDepth().setVal(depth);
            }
        }
    }

    public Integer getGapWidth() {
        if (chart.isSetGapWidth()) {
            return chart.getGapWidth().getVal();
        } else {
            return null;
        }
    }

    public void setGapWidth(Integer width) {
        if (width == null) {
            if (chart.isSetGapWidth()) {
                chart.unsetGapWidth();
            }
        } else {
            if (chart.isSetGapWidth()) {
                chart.getGapWidth().setVal(width);
            } else {
                chart.addNewGapWidth().setVal(width);
            }
        }
    }

    public Shape getShape() {
        if (chart.isSetShape()) {
            return Shape.valueOf(chart.getShape().getVal());
        } else {
            return null;
        }
    }

    public void setShape(Shape shape) {
        if (shape == null) {
            if (chart.isSetShape()) {
                chart.unsetShape();
            }
        } else {
            if (chart.isSetShape()) {
                chart.getShape().setVal(shape.underlying);
            } else {
                chart.addNewShape().setVal(shape.underlying);
            }
        }
    }

    @Override
    public XDDFChartData.Series addSeries(XDDFDataSource<?> category,
            XDDFNumericalDataSource<? extends Number> values) {
        final int index = this.series.size();
        final CTBarSer ctSer = this.chart.addNewSer();
        ctSer.addNewTx();
        ctSer.addNewCat();
        ctSer.addNewVal();
        ctSer.addNewIdx().setVal(index);
        ctSer.addNewOrder().setVal(index);
        final Series added = new Series(ctSer, category, values);
        this.series.add(added);
        return added;
    }

    public class Series extends XDDFChartData.Series {
        private CTBarSer series;

        protected Series(CTBarSer series, XDDFDataSource<?> category,
                XDDFNumericalDataSource<? extends Number> values) {
            super(category, values);
            this.series = series;
        }

        protected Series(CTBarSer series, CTAxDataSource category, CTNumDataSource values) {
            super(XDDFDataSourcesFactory.fromDataSource(category), XDDFDataSourcesFactory.fromDataSource(values));
            this.series = series;
        }

        @Override
        protected CTSerTx getSeriesText() {
            if (series.isSetTx()) {
                return series.getTx();
            } else {
                return series.addNewTx();
            }
        }

        @Override
        public void setShowLeaderLines(boolean showLeaderLines) {
            if (!series.isSetDLbls()) {
                series.addNewDLbls();
            }
            if (series.getDLbls().isSetShowLeaderLines()) {
                series.getDLbls().getShowLeaderLines().setVal(showLeaderLines);
            } else {
                series.getDLbls().addNewShowLeaderLines().setVal(showLeaderLines);
            }
        }

        @Override
        public XDDFShapeProperties getShapeProperties() {
            if (series.isSetSpPr()) {
                return new XDDFShapeProperties(series.getSpPr());
            } else {
                return null;
            }
        }

        @Override
        public void setShapeProperties(XDDFShapeProperties properties) {
            if (properties == null) {
                if (series.isSetSpPr()) {
                    series.unsetSpPr();
                }
            } else {
                if (series.isSetSpPr()) {
                    series.setSpPr(properties.getXmlObject());
                } else {
                    series.addNewSpPr().set(properties.getXmlObject());
                }
            }
        }

        @Override
        protected CTAxDataSource getAxDS() {
            return series.getCat();
        }

        @Override
        protected CTNumDataSource getNumDS() {
            return series.getVal();
        }

        @Override
        public void setIndex(long val) {
            series.getIdx().setVal(val);
        }

        @Override
        public void setOrder(long val) {
            series.getOrder().setVal(val);
        }
    }
}
