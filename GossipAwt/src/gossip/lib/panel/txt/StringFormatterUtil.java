package gossip.lib.panel.txt;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Formatter;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.UnknownFormatConversionException;

import org.apache.logging.log4j.Logger;

import gossip.keyboard.input.InputItemId;
import gossip.lib.util.MyLogger;
import gossip.lib.util.StringUtil;
import gossip.manager.LanguageManager;
import gossip.util.DrawingUtil;

public class StringFormatterUtil {

	static GraphicText[] formatsGraphicsFixedPosition(Graphics g, String text, Point p, Font font, int lines) {
		GraphicText[] graphicTexts = new GraphicText[lines];
		GraphicText gt = new GraphicText(text);
		gt.setFont(font);
		if (p != null) {
			gt.setRectangle(new Rectangle(p.x, p.y, 0, 0));
		}
		graphicTexts[0] = gt;
		return graphicTexts;
	}

	public enum Alignment {
		CENTER,
		LEFT,
		RIGHT
	}

	private static final int FORMAT_STEP_SMALL = 1;
	private static final int FORMAT_STEP_BIG = 3;

	static GraphicText[] formatsGraphics(Graphics g, String text, Rectangle targetRect, Font font, int lines, Alignment alignment) {
		GraphicText[] graphicTexts = _formatsGraphics(g, text, targetRect, font, lines, alignment);
		switch (alignment) {
		case LEFT:
			leftJustifyText(graphicTexts, targetRect);
			break;
		case RIGHT:
			rightJustifyText(graphicTexts, targetRect);
			break;
		default:
			centerText(graphicTexts, targetRect);
			break;
		}
		return graphicTexts;

	}

	private static GraphicText[] _formatsGraphics(Graphics g, String text, Rectangle targetRect, Font font, int lines, Alignment alignment) {
		int maxLength = text.length();
		boolean done = false;
		GraphicText[] graphicTexts = new GraphicText[lines];
		g.setFont(font);
		int formatStep;
		if (maxLength > 20 || lines > 1) {
			formatStep = FORMAT_STEP_BIG;
		} else {
			formatStep = FORMAT_STEP_SMALL;
		}
		FontMetrics fm = g.getFontMetrics();
		StringBuilder buffer = new StringBuilder();
		SplitterIndex splitter = new SplitterIndex();
		do {
			done = true;
			String[] strings = splitText(text, maxLength, lines, buffer, splitter);
			for (int i = 0; i < graphicTexts.length; i++) {
				if (strings[i] != null) {
					GraphicText gt = calculateFormat(g, strings[i], targetRect, fm);
					if (gt == null) {
						done = false;
						maxLength -= formatStep;
						if (maxLength < 1) {
							return graphicTexts;
						}
						break;
					} else {
						gt.setFont(font);
						graphicTexts[i] = gt;
					}
				}
			}
		} while (!done);
		return graphicTexts;
	}

	public static String[] splitText(String text, int maxLength, int maxNum, StringBuilder buffer, SplitterIndex splitter) {
		String[] resultStrings = new String[maxNum];
		if (maxLength > 0) {
			if (maxLength == 1) {
				resultStrings[0] = text;
			} else {
				int computed = 0;
				int idx = 0;
				while (computed < text.length() && idx < maxNum) {
					buffer.setLength(0);
					int resultIdx = getTextPart(text.substring(computed), buffer, maxLength, (idx < maxNum - 1), splitter);
					computed += resultIdx;
					resultStrings[idx] = buffer.toString();
					if (resultIdx <= 0) {
						break;
					}
					idx++;
				}
			}
		}
		return resultStrings;
	}

	private static class Splitter {

		private final Character c;
		private final boolean isWhitespace;

		public Splitter(Character c, boolean isWhitespace) {
			super();
			this.c = c;
			this.isWhitespace = isWhitespace;
		}

		public boolean isWhitespace() {
			return isWhitespace;
		}

		public Character getC() {
			return c;
		}
	}

	public static class SplitterIndex {
		private Splitter splitter;
		private int index = 0;

		public void setSplitter(final Splitter splitter) {
			this.splitter = splitter;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public Splitter getSplitter() {
			return splitter;
		}

		public int getIndex() {
			return index;
		}

		private void decrementIndex() {
			index--;
		}

		private void incrementIndex() {
			index++;
		}
	}

	private static final Splitter[] SPLITTER = {
			new Splitter(' ', true),
			new Splitter('#', false),
			// new Splitter('=', false),
			new Splitter('-', false),
			new Splitter(':', false),
			new Splitter(',', false),
			// new Splitter('.', false)
	};

	private static final int TEXT_CUT_MIN = 14;

	private static SplitterIndex clearSplitterIndex(String text, int startIdx, SplitterIndex splitterIndex) {
		splitterIndex.setIndex(0);
		for (int i = 0; i < SPLITTER.length; i++) {
			int idx1 = text.lastIndexOf(SPLITTER[i].getC(), startIdx);
			if (splitterIndex.index < idx1) {
				splitterIndex.index = idx1;
				splitterIndex.setSplitter(SPLITTER[i]);
			}
		}
		return splitterIndex;
	}

	/**
	 * First we try to split at whitespace. If this fails we just break at maxNum.
	 * If the last string exceeds the maxLength limit we just cut it.
	 * 
	 * @param text     The text to split
	 * @param maxLegth Max length of any result text
	 * @param maxNum   Maximum of result texts
	 * @return
	 */
	private static int getTextPart(String text, StringBuilder resultString, int maxLegth, boolean isSplitWords, SplitterIndex splitter) {
		splitter = clearSplitterIndex(text, 0, splitter);
		splitter.setIndex(text.length());
		if (text.length() <= maxLegth) {
			resultString.append(text);
		} else {
			if (isSplitWords) {
				do {
					if (splitter.getIndex() > 0) {
						splitter.decrementIndex();
					}
					splitter = clearSplitterIndex(text, splitter.getIndex(), splitter);
				} while (splitter.getIndex() > maxLegth);
				if (splitter.getIndex() > 0) {
					resultString.append(text.substring(0, splitter.getIndex() + (splitter.getSplitter().isWhitespace() ? 0 : 1)));
					splitter.incrementIndex();
				} else {
					resultString.append(text.substring(0, maxLegth));
					splitter.setIndex(maxLegth);
				}
			} else {
				if (maxLegth > TEXT_CUT_STRING_LEN * 2) {
					if (text.length() < TEXT_CUT_MIN || maxLegth < TEXT_CUT_MIN) { // am Ende abschneiden
						int end = maxLegth - TEXT_CUT_STRING_LEN;
						if (end < 1) {
							end = 1; // Sonderfall TEXT_CUT_STRING ist
										// länger
										// als die Buchstaben, die wir
										// abschneiden
						}
						if (end > text.length() - 1) {
							end = text.length() - 1;
						}
						resultString.append(text.substring(0, end));
						resultString.append(TEXT_CUT_STRING);
					} else {
						int part = (maxLegth - TEXT_CUT_STRING_LEN) / 2;
						int cor = 0;
						if ((2 * part + TEXT_CUT_STRING_LEN) < maxLegth) {
							cor = 1;
						}

						int start1 = 0; // text.length() - maxLegth
						int end1 = part + cor;

						int start2 = text.length() - part;
						resultString.append(text.substring(start1, end1));
						resultString.append(TEXT_CUT_STRING);
						resultString.append(text.substring(start2, text.length()));
					}
				} else {
					int end = maxLegth - 1;
					if (end < 1) {
						end = 1;
					}
					if (end > text.length() - 1) {
						end = text.length() - 1;
					}
					resultString.append(text.substring(0, end));
					resultString.append('~');
				}
				splitter.setIndex(maxLegth);
			}
		}

		return splitter.getIndex();
	}

	private static final int FONT_BASELINE_CORRECTION = 5;
	private static final int FONT_OFFSET_CORRECTION = 0;

	/**
	 * Returns a GraphicText object if the text fits in the target (only in respect
	 * of the with)
	 * 
	 */
	private static GraphicText calculateFormat(Graphics g, String text, Rectangle targetRect, FontMetrics fm) {
		GraphicText gText = null;
		if (!StringUtil.isNullOrEmpty(text)) {

			int w = fm.stringWidth(text);
			int h = fm.getHeight();

			if ((targetRect.width - (2 * TEXT_GAB_X)) >= w || text.length() <= TEXT_CUT_STRING_LEN) {
				gText = new GraphicText(text);
				gText.setRectangle(new Rectangle(w, h - FONT_BASELINE_CORRECTION));
			}
		}
		return gText;
	}

	public static final int TEXT_GAB_Y = 2;
	public static final int TEXT_GAB_X = 2;

	private static final String TEXT_CUT_STRING = "...";
	private static final int TEXT_CUT_STRING_LEN = 2;

	private static final int calculateHeight(GraphicText[] texts) {
		int height = 0;
		for (int i = 0; i < texts.length; i++) {
			if (texts[i] != null) {
				height += texts[i].getRectangle().height + TEXT_GAB_Y;
			}
		}
		return height;
	}

	private static final int calculateWith(GraphicText[] texts) {
		int maxWith = 0;
		for (int i = 0; i < texts.length; i++) {
			if (texts[i] != null) {
				if (texts[i].getRectangle().width > maxWith) {
					maxWith = texts[i].getRectangle().width;
				}
			}
		}
		return maxWith;
	}

	private static void applyPositions(GraphicText[] texts, int startX, int startY) {
		for (int i = 0; i < texts.length; i++) {
			if (texts[i] != null) {
				texts[i].getRectangle().x = startX;
				texts[i].getRectangle().y = startY + texts[i].getFont().getSize() - 2;
				startY += texts[i].getRectangle().height + TEXT_GAB_Y;
			}
		}
	}

	/**
	 * Center the longest text and left justify the rest to this line.
	 * 
	 * @param texts
	 * @param tragetRect
	 */
	private static void centerText(GraphicText[] texts, Rectangle targetRect) {
		int maxWith = calculateWith(texts);
		int height = calculateHeight(texts);
		int startX = (targetRect.width - maxWith) / 2 + targetRect.x;
		int startY = (targetRect.height - height) / 2 + targetRect.y + FONT_OFFSET_CORRECTION;
		applyPositions(texts, startX, startY);
	}

	/**
	 * Center the longest text and left justify the rest to this line.
	 * 
	 * @param texts
	 * @param tragetRect
	 */
	private static void leftJustifyText(GraphicText[] texts, Rectangle targetRect) {
		int height = calculateHeight(texts);
		int startX = targetRect.x + TEXT_GAB_X;
		int startY = (targetRect.height - height) / 2 + targetRect.y;
		applyPositions(texts, startX, startY);
	}

	/**
	 * Center the longest text and left justify the rest to this line.
	 * 
	 * @param texts
	 * @param tragetRect
	 */
	private static void rightJustifyText(GraphicText[] texts, Rectangle targetRect) {
		int maxWith = calculateWith(texts);
		int height = calculateHeight(texts);
		int startX = targetRect.x + (targetRect.width - TEXT_GAB_X - maxWith);
		int startY = (targetRect.height - height) / 2 + targetRect.y;
		applyPositions(texts, startX, startY);
	}

	public static void drawGraphicTexts(Graphics g, final GraphicText[] texts) {
		Graphics2D g2d = DrawingUtil.getGraphics2d(g);
		try {
			for (int i = 0; i < texts.length; i++) {
				GraphicText gt = texts[i];
				if (gt != null) {
					g2d.setFont(gt.getFont());
					g2d.drawString(gt.getText(), gt.getRectangle().x, gt.getRectangle().y);
				}
			}
		} finally {
			g2d.dispose();
		}
	}

	private static final Logger logger = MyLogger.getLog(StringFormatterUtil.class);

	private static String formatStringValue(Object value, boolean isHtmlText) {
		StringBuilder sb = new StringBuilder();
		if (isHtmlText) {
			sb.append("<b>'");
		}
		sb.append(valueToString(value));
		if (isHtmlText) {
			sb.append("'</b>");
		}
		return sb.toString();
	}

	private static String valueToString(Object value) {
		if (value != null) {
			if (value instanceof InputItemId) {
				return LanguageManager.getLocaleText((InputItemId) value);
			} else {
				return value.toString();
			}
		} else {
			return "";
		}
	}

	public static String getFormatedText(String formatStr, List<?> valueList, boolean isHtmlText) {
		String resultStr = null;
		if (valueList == null || valueList.isEmpty()) {
			resultStr = formatStr;
		} else {
			if (StringUtil.isNullOrEmpty(formatStr) || LanguageManager.isUnresolvedString(formatStr)) {
				StringBuilder sb = new StringBuilder();
				sb.append(formatStr);
				for (int i = 0; i < valueList.size(); i++) {
					sb.append(">> %s <<");
				}
				formatStr = sb.toString();
			}
			StringBuilder sb = new StringBuilder();
			Formatter formatter = new Formatter(sb);
			try {
				switch (valueList.size()) {
				case 0:
					formatter.format(formatStr);
					break;
				case 1:
					formatter.format(formatStr, formatStringValue(valueList.get(0), isHtmlText));
					break;
				case 2:
					formatter.format(formatStr, formatStringValue(valueList.get(0), isHtmlText), formatStringValue(valueList.get(1), isHtmlText));
					break;
				case 3:
					formatter.format(formatStr, formatStringValue(valueList.get(0), isHtmlText), formatStringValue(valueList.get(1), isHtmlText),
							formatStringValue(valueList.get(2), isHtmlText));
					break;
				default:
					formatter.format(formatStr, formatStringValue(valueList.get(0), isHtmlText), formatStringValue(valueList.get(1), isHtmlText),
							formatStringValue(valueList.get(2), isHtmlText), formatStringValue(valueList.get(3), isHtmlText));
					break;
				}
				resultStr = sb.toString();
			} catch (UnknownFormatConversionException e) {
				logger.error("input={}", formatStr);
				MyLogger.printExecption(logger, e);
				resultStr = formatStr;
			} catch (IllegalFormatException e) {
				MyLogger.printExecption(logger, e);
				resultStr = formatStr;
			} catch (Exception e) {
				MyLogger.printExecption(logger, e);
				resultStr = formatStr;
			}
			formatter.close();
		}
		return resultStr;
	}

}
