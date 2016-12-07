package edu.up.numeds;

/**
 * A utility for writing CSV files for Doubles only
 *  Warning: This utility DOES NOT check for compliance with RFC4180
 * See https://tools.ietf.org/html/rfc4180 for more information
 *
 * @author      Vincent Fiestada <vffiestada@gmail.com>
 * @version     1.0             
 * @since       0.4          
 */

import java.io.IOException;
import java.io.Writer;
import java.lang.Iterable;
import java.util.ArrayList;

public class CSVWriter {

	public CSVWriter(Writer w)
	{
		_writer = w;
	}

    public CSVWriter(Writer w, Iterable<String> headers) throws IOException
	{
		_writer = w;
        boolean first = true; // No separator before the first value
        StringBuilder sb = new StringBuilder();
        for (String h : headers) 
        {
            if (!first) 
            {
                sb.append(COLUMN_SEPARATOR);
            }
            first = false;
            sb.append(h);
        }
        sb.append(ROW_SEPARATOR);
        _writer.append(sb.toString());
	}

	private Writer _writer;

	// Constants 
	private static final char COLUMN_SEPARATOR = ',';
	private static final char ROW_SEPARATOR = '\n';

    public void row(Iterable<Double> values) throws IOException
    {
        row(values, "");
    }

    public void row(Iterable<Double> values, String comment) throws IOException 
    {
        boolean first = true; // No separator before the first value
		StringBuilder sb = new StringBuilder();
        for (Double value : values) {
            if (!first) 
            {
                sb.append(COLUMN_SEPARATOR);
            }
            first = false;
			sb.append(value.toString());
        }
        sb.append(COLUMN_SEPARATOR);
        sb.append(comment);
        sb.append(ROW_SEPARATOR);
        _writer.append(sb.toString());
    }

    public void rowOrderedPair(Double x, Double y) throws IOException 
    {
        rowOrderedPair(x, y, "");
    }

    public void rowOrderedPair(Double x, Double y, String comment) throws IOException 
    {
        ArrayList<Double> l = new ArrayList();
        l.add(x);
        l.add(y);
        row(l, comment);
    }

    public void finish() throws IOException
    {
        _writer.flush();
        _writer.close();
    }
}