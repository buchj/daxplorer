package io.rightpad.daxplorer.visualization;

import io.rightpad.daxplorer.visualization.charts.Chart;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class ChartPanel extends JPanel
{
    private PointF position = new PointF(0, 0);
    private float chartWidth = 100, chartHeight = 100;

    private List<ChartEntry> charts = new LinkedList<>();


    public PointF getPosition()
    {
        return position;
    }

    public void setPosition(PointF position)
    {
        System.out.println("Changing position from " + this.position + " to " + position);
        this.position = position;
        repaint();
    }

    public void setPositionY(float posY)
    {
        setPosition(new PointF(getPosition().getX(), posY));
    }

    public float getChartWidth()
    {
        return chartWidth;
    }

    public void setChartWidth(float chartWidth)
    {
        System.out.println("Changing chart width from " + this.chartWidth + " to " + chartWidth);
        this.chartWidth = chartWidth;
        repaint();
    }

    public float getChartHeight()
    {
        return chartHeight;
    }

    public void setChartHeight(float chartHeight)
    {
        System.out.println("Changing chart height from " + this.chartHeight + " to " + chartHeight);
        this.chartHeight = chartHeight;
        repaint();
    }

    private float getWidthRelation()
    {
        return getWidth() / this.chartWidth;
    }

    private float getHeightRelation()
    {
        return getHeight() / this.chartHeight;
    }

    public int toAbsoluteX(float relX)
    {
        return (int) ((relX - this.position.getX()) * getWidthRelation());
    }

    public int toAbsoluteY(float relY)
    {
        return getHeight() - (int) ((relY - this.position.getY()) * getHeightRelation());
    }

    public float toRelativeX(int absX)
    {
        return absX / getWidthRelation() + this.position.getX();
    }

    public float toRelativeY(int absY)
    {
        return (getHeight() - absY) / getHeightRelation() + this.position.getY();
    }

    public void addChart(Chart chart)
    {
        System.out.println("Adding chart " + chart);
        this.charts.add(new ChartEntry(chart));
        repaint();
    }

    public void setChartEnabled(Chart chart, boolean enabled)
    {
        this.charts.stream()
                .filter(c -> c.chart == chart)
                .forEach(c -> c.enabled = enabled);
    }

    public void removeChart(Chart chart)
    {
        this.charts.removeIf(c -> c.chart == chart);
    }

    private static final Color AXIS_COLOR = Color.DARK_GRAY;
    private static final Stroke AXIS_STROKE = new BasicStroke(2);

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        paintCharts(g2d);
        drawYAxis(g2d);
        drawXAxis(g2d);
    }

    private void paintCharts(Graphics2D g2d)
    {
        this.charts.forEach(c -> c.chart.draw(this, g2d));
    }

    private void drawYAxis(Graphics2D g2d)
    {
        g2d.setColor(AXIS_COLOR);
        g2d.setStroke(AXIS_STROKE);
        g2d.drawLine(10, 0, 10, getHeight());
        drawYStepLines(g2d);
    }

    private void drawYStepLines(Graphics2D g2d)
    {
        float stepWidth = this.chartHeight / 10;
        int start = (int) (this.position.getY() / stepWidth);
        int end = (int) ((this.position.getY() + this.chartHeight) / stepWidth) + 1;
        for(int i = start; i < end; i++) {
            int relStepLineY = (int) (i * stepWidth);
            int stepLineY = toAbsoluteY(relStepLineY);
            g2d.drawLine(5, stepLineY, 15, stepLineY);
            g2d.drawString("" + relStepLineY, 20, stepLineY);
        }
    }

    private void drawXAxis(Graphics2D g2d)
    {
        int xAxisYCoord = getHeight() - 15;
        g2d.setStroke(AXIS_STROKE);
        g2d.setColor(AXIS_COLOR);
        g2d.drawLine(0, xAxisYCoord, getWidth(), xAxisYCoord);

        drawXStepLines(g2d);
    }

    private void drawXStepLines(Graphics2D g2d)
    {
        float stepWidth = 1;
        int start = (int) (this.position.getX() / stepWidth);
        int end = (int) ((this.position.getX() + this.chartWidth) / stepWidth) + 1;
        for(int i = start; i < end; i++) {
            int relStepLineX = (int) (i * stepWidth);
            int stepLineX = toAbsoluteX(relStepLineX);
            g2d.drawLine(stepLineX, getHeight() - 10, stepLineX, getHeight() - 20);
            g2d.drawString("" + relStepLineX, stepLineX - 5, getHeight());
        }
    }

    private static class ChartEntry
    {
        final Chart chart;
        boolean enabled;

        public ChartEntry(Chart chart)
        {
            this.chart = chart;
            this.enabled = true;
        }
    }
}
