package org.daisy.braille.utils.pef;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.daisy.braille.utils.api.table.TableCatalogService;

/**
 * Provides a facade for text converter.
 * @author Joel Håkansson
 *
 */
public class TextConverterFacade {
	/**
	 * Defines a date format (yyyy-MM-dd)
	 */
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * Key for parseTextFile setting,
	 * corresponding settings value should contain the title of the publication
	 */
	public static final String KEY_TITLE = "title";
	/**
	 * Key for parseTextFile setting,
	 * corresponding settings value should contain the author of the publication
	 */
	public static final String KEY_AUTHOR = "author";
	/**
	 * Key for parseTextFile setting,
	 * corresponding settings value should contain the identifier for the publication 
	 */
	public static final String KEY_IDENTIFIER = "identifier";
	/**
	 * Key for parseTextFile setting,
	 * corresponding settings value should match the table to use
	 */
	public static final String KEY_MODE = "mode";
	/**
	 * Key for parseTextFile setting,
	 * corresponding settings value should contain the language of the publication
	 */
	public static final String KEY_LANGUAGE = "language";
	/**
	 * Key for parseTextFile setting,
	 * corresponding settings value should be "true" for duplex or "false" for simplex
	 */
	public static final String KEY_DUPLEX = "duplex";
	/**
	 * Key for parseTextFile setting,
	 * corresponding settings value should be a string containing a valid date on the form yyyy-MM-dd
	 */
	public static final String KEY_DATE = "date";

	static final String KEY_ROWS_LIMIT = "rows-limit";
	static final String KEY_COLS_LIMIT = "cols-limit";

	private final TableCatalogService factory;

	/**
	 * Creates a new text converter facade with the specified
	 * table catalog.
	 * @param factory the table catalog
	 */
	public TextConverterFacade(TableCatalogService factory) {
		this.factory = factory;
	}

	/**
	 * Parses a text file and outputs a PEF-file based on the contents of the file
	 * @param input input text file
	 * @param output output PEF-file
	 * @param settings settings
	 * @throws IOException if IO fails
	 */
	public void parseTextFile(File input, File output, Map<String, String> settings) throws IOException {
		TextHandler.Builder builder = new TextHandler.Builder(input, output, factory);
		for (String key : settings.keySet()) {
			String value = settings.get(key);
			switch (key) {
			case KEY_TITLE:
				builder.title(value);
				break;
			case KEY_AUTHOR:
				builder.author(value);
				break;
			case KEY_IDENTIFIER:
				builder.identifier(value);
				break;
			case KEY_MODE:
				builder.converterId(value);
				break;
			case KEY_LANGUAGE:
				builder.language(value);
				break;
			case KEY_DUPLEX:
				builder.duplex("true".equalsIgnoreCase(value));
				break;
			case KEY_DATE:
				try {
					builder.date(DATE_FORMAT.parse(value));
				} catch (ParseException e) { throw new IllegalArgumentException(e); }
				break;
			case KEY_ROWS_LIMIT:
				builder.rowsLimit(Integer.parseInt(value));
				break;
			case KEY_COLS_LIMIT:
				builder.colsLimit(Integer.parseInt(value));
				break;
			default:
				throw new IllegalArgumentException("Unknown option \"" + key + "\"");
			}

		}
		TextHandler tp = builder.build();
		tp.parse();
	}	
}
