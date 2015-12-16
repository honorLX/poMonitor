package pomonitor.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "Dali", date = "2015-12-16T15:24:18.067+0800")
@StaticMetamodel(Emotionalword.class)
public class Emotionalword_ {
	public static volatile SingularAttribute<Emotionalword, Integer> id;
	public static volatile SingularAttribute<Emotionalword, String> word;
	public static volatile SingularAttribute<Emotionalword, String> speech;
	public static volatile SingularAttribute<Emotionalword, Integer> polarity;
	public static volatile SingularAttribute<Emotionalword, Integer> strength;
}
