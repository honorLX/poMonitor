package pomonitor.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "Dali", date = "2015-12-16T15:24:19.257+0800")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, Integer> userid;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, String> userpwd;
	public static volatile SingularAttribute<User, String> userlevel;
	public static volatile SetAttribute<User, Sensword> senswords;
}
