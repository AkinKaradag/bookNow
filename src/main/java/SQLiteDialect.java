import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        super();
    }

    @Override
    public void initializeFunctionRegistry(FunctionContributions functionContributions) {
        // Register SQL functions
        functionContributions.getFunctionRegistry().register(
                "lower",
                new StandardSQLFunction("lower", StandardBasicTypes.STRING)
        );
    }

    @Override
    public boolean supportsIfExistsBeforeTableName() {
        return true;
    }

    @Override
    public boolean supportsIfExistsAfterTableName() {
        return true;
    }

    @Override
    public boolean dropConstraints() {
        // SQLite doesn't support drop constraints
        return false;
    }

    @Override
    public boolean hasAlterTable() {
        // SQLite has limited ALTER TABLE support
        return false;
    }
}