package se.mah.kd330a.project.TipOfTheDay;


import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import org.mcsoxford.rss.RSSItem;

/*
 * @author asampe
 * 
 * @author marcusmansson
 * 
 * this is a wrapper class for RSSItem, adding features such as making the
 * articles sortable, and method for getting a summary text for large content
 * 
 * version history:
 * 0.7.2 added Serializable, changed constructor // marcusmansson
 * 
 */
public class Tip implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static final int maxSummaryLength = 80;
	private transient RSSItem rssItem;
	private boolean textVisible;
	private String articleCourseCode;
	private String header;
	private String date;
	private String text;

	public Tip(String header, String date, String text)
	{
		super();

		textVisible = true;

		this.header = header;
		this.date = date;
		this.text = text;
	}

	public String getHeader()
	{
		return header;
	}

	public String getDate()
	{
		return date;
	}

	public String getText()
	{
		return text;
	}

	public String getArticleSummary()
	{
		String summary = getText();

		if (summary.length() > maxSummaryLength)
		{
			return summary.substring(0, maxSummaryLength) + "...";
		}
		else
		{
			return summary;
		}
	}

	public boolean isTextVisible()
	{
		return textVisible;
	}

	public void setTextVisible(boolean value)
	{
		textVisible = value;
	}

	public String toString()
	{
		return getHeader();
	}
}
