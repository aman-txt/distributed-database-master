package parser;

public class RegexConstant {

	public static final String CREATE_TABLE_REGEX="((create)\\s+(table)\\s*(\\w+.)?)\\s*([(](\\s*\\w+\\s+(int|varchar|boolean)(\\s+primary\\s+key|\\s+foreign\\s+key\\s+references\\s+\\w+\\s*[(]\\s*\\w+\\s*[)])?\\s*,?)+\\s*[)]);";

	public static final String GET_COLUMNS_REGEX="([(]\\s*[(\\w+\\s+(INT|varchar|float|boolean),[)]]+)";

	public static final String INSERT_WITH_COLUMN="((?i)(INSERT INTO)\\s+(\\S+)\\s+\\((.*?)\\)\\s+(VALUES).*\\s+\\((.*?)\\);)";

	public static final String INSERT_WITHOUT_COLUMN="((?i)(INSERT INTO)\\s+(\\S+).*\\s+(VALUES)\\s+\\((.*?)\\);)";

	public static final String CEHCK_COLUMN_REGEX="([)]\\s+Values)";

	public static final String DELETE_REGEX="(?i)(DELETE FROM)\\s+(\\w+)(\\s+(where)?\\s+(.*)?)?;";

	public static final String SELECT_REGEX="((select)\\s+([]|((\\w+),?\\s)+)\\s+(from)\\s+(\\w+)\\s*(where\\s+(\\w+\\s*(=|>|<|>=|<=)\\s*\\w+))?\\s*;)";

	public static final String UPDATE_REGEX="((UPDATE\\s+(\\w+))\\s+SET\\s+(\\w+\\s*=\\s*'[a-zA-z0-9 ]+')(\\s+WHERE\\s+(\\w+\\s*(=|<|>)\\s*'[a-zA-z0-9 ]+'))?\\s*;)";

	public static final String USE_REGEX="((use)\\s+(\\w+)\\s*;)";

	public static final String CREATE_DATA_REGEX="((create)\\s+(database)\\s+(\\w+)\\s*;)";
}
