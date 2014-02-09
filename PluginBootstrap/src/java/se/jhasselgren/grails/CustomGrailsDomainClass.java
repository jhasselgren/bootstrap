package se.jhasselgren.grails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass;

public class CustomGrailsDomainClass extends DefaultGrailsDomainClass{

	public CustomGrailsDomainClass(DefaultGrailsDomainClass parent){
		
		this(parent.getClazz());
	}
	
	
	public CustomGrailsDomainClass(Class<?> clazz, Map<String, Object> defaultConstraint){
		super(clazz, defaultConstraint);
		
		customLayout = getCustomLayoutList();
	}
	
	public CustomGrailsDomainClass(Class<?> clazz) {
		this(clazz, Collections.<String, Object> emptyMap());
	}
	
	public Collection<String> getCustomLayout(){
		return customLayout;
	}
	
	private Collection<String> customLayout;
	
	private Collection<String> getCustomLayoutList(){
		Collection<String> potentialList = new ArrayList<String>();
		
		if(getStaticPropertyValue("customLayout", Collection.class) == null){
			return potentialList;
		}
		
		for(Object o : getStaticPropertyValue("customLayout", Collection.class)){
			if (o instanceof String) {
				potentialList.add((String) o);
			}
		}
		
		return potentialList;
		
	}
	
}
