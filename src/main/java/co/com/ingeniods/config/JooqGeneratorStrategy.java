package co.com.ingeniods.config;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;

public class JooqGeneratorStrategy extends DefaultGeneratorStrategy {

	 @Override
	    public String getJavaClassName(Definition definition, Mode mode) {
		 	if(mode != Mode.POJO) {
		 		return super.getJavaClassName(definition,mode);
		 	}
		 	return super.getJavaClassName(definition,mode) + "Pojo";
	    }
}