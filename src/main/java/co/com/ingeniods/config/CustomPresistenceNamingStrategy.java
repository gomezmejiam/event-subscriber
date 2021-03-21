package co.com.ingeniods.config;

import java.util.Objects;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitForeignKeyNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomPresistenceNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl
		implements PhysicalNamingStrategy {

	private static final long serialVersionUID = -4824306240368398184L;
	private final String NAME_REGEX = "([a-z])([A-Z])";
	private final String NAME_REPLACEMENT = "$1_$2";
	private final int FK_MAX_TABLE_NAME = 22;

	@Override
	public Identifier toPhysicalCatalogName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
		return convertToSnakeCase(identifier);
	}

	@Override
	public Identifier toPhysicalColumnName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
		return convertToSnakeCase(identifier);
	}

	@Override
	public Identifier toPhysicalSchemaName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
		return convertToSnakeCase(identifier);
	}

	@Override
	public Identifier toPhysicalSequenceName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
		return convertToSnakeCase(identifier);
	}

	@Override
	public Identifier toPhysicalTableName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
		return convertToSnakeCase(identifier);
	}

	@Override
	public Identifier determineForeignKeyName(ImplicitForeignKeyNameSource source) {
		return toIdentifier(generateForeingKeyName(source), source.getBuildingContext());
	}

	private String generateForeingKeyName(ImplicitForeignKeyNameSource fkNameSource) {
		String table = fkNameSource.getTableName().getCanonicalName();
		if (fkNameSource.getTableName().getCanonicalName().length() > FK_MAX_TABLE_NAME) {
			table = table.substring(0, FK_MAX_TABLE_NAME);
		}
		String fkName = "FK_" + table + "_" + fkNameSource.getReferencedTableName().getCanonicalName();
		return fkName.toUpperCase();
	}

	private Identifier convertToSnakeCase(final Identifier identifier) {
		if (Objects.isNull(identifier))
			return null;
		return formatIdentifier(identifier);
	}

	private Identifier formatIdentifier(final Identifier identifier) {
		return Identifier.toIdentifier(formatName(identifier));
	}

	private String formatName(final Identifier identifier) {
		String name = identifier.getText().replaceAll(NAME_REGEX, NAME_REPLACEMENT).toUpperCase();
		if (name.contains("_DTO")) {
			name = name.replaceAll("_DTO", "");
		}
		return name;
	}
}