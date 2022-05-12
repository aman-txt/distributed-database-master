package QueryContainer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.RegexConstant;
import query.container.WhereCond;

public class UpdateQueryProcessor {

	private String tableName; // name of the table

	private String columnInWhere; // L.H.S of the where clause

	private WhereCond whereCond; // Condition of the where clause

	private String factor; // R.H.S in where clause

	private String colTobeUpdate;

	private String colValueTobeSet;

	public void parseUpdateQuery(String updateQuery) {

		if (updateQuery.toLowerCase().contains("where")) {
			queryWithWhere(updateQuery);
		} else {
			queryWithoutWhere(updateQuery);
		}
	}

	private void queryWithoutWhere(String query) {

		final Matcher matcher = pattern(query);

		while (matcher.find()) {
			this.tableName = matcher.group(3);

			String[] st = matcher.group(4).split("=");
			this.colTobeUpdate = st[0];
			this.colValueTobeSet = st[1];

		}

	}

	private void queryWithWhere(String query) {
		final Matcher matcher = pattern(query);

		while (matcher.find()) {

			this.tableName = matcher.group(3);

			String[] st = matcher.group(4).split("=");
			this.colTobeUpdate = st[0];
			this.colValueTobeSet = st[1];
			this.colValueTobeSet = this.colValueTobeSet.substring(1,this.colValueTobeSet.length()-1);

			getOpeTokens(matcher.group(6));
		}

	}

	private Matcher pattern(String query) {
		final Pattern pattern = Pattern.compile(RegexConstant.UPDATE_REGEX,
				Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(query);
		return matcher;
	}

	private void getOpeTokens(String stat) {

		String values[] = null;
		if (stat.contains("=")) {

			values = stat.split("=");
			this.whereCond = WhereCond.EQUALS;
		} else if (stat.contains(">")) {
			values = stat.split(">");
			this.whereCond = WhereCond.GREATER_THAN;
		} else if (stat.contains("<")) {
			values = stat.split("<");
			this.whereCond = WhereCond.LESS_THAN;
		}
		this.columnInWhere = values[0].trim();
		this.factor = values[1].trim();
		this.factor = this.factor.substring(1,this.factor.length()-1);

	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnInWhere() {
		return columnInWhere;
	}

	public void setColumnInWhere(String columnInWhere) {
		this.columnInWhere = columnInWhere;
	}

	public WhereCond getWhereCond() {
		return whereCond;
	}

	public void setWhereCond(WhereCond whereCond) {
		this.whereCond = whereCond;
	}

	public String getFactor()  {

		return factor;
	}

	public void setFactor(String factor) {
		this.factor = factor;
	}

	public String getColTobeUpdate() {
		return colTobeUpdate;
	}

	public void setColTobeUpdate(String colTobeUpdate) {
		this.colTobeUpdate = colTobeUpdate;
	}

	public String getColValueTobeSet() {

		return colValueTobeSet;
	}

	public void setColValueTobeSet(String colValueTobeSet) {
		this.colValueTobeSet = colValueTobeSet;
	}

	@Override
	public String toString() {
		return "UpdateQueryProcessor [tableName=" + tableName + ", columnInWhere=" + columnInWhere + ", whereCond="
				+ whereCond + ", factor=" + factor + ", colTobeUpdate=" + colTobeUpdate + ", colValueTobeSet="
				+ colValueTobeSet + "]";
	}

}
