# Attempt 1 – Declaration Kind Comments

One solution is to use separator comments for different declarations:

{% code title="Person.java" %}
```java
class Person
{
	// =============== Fields ===============

	String firstName;
	String lastName;

	// =============== Methods ===============
}
```
{% endcode %}

{% code title="Person\_edited.java" %}
```java
class Person
{
	// =============== Fields ===============

	String firstName;
	String lastName;

	// =============== Methods ===============

	String getFullName()
	{
		return firstName + " " + lastName;
	}
}
```
{% endcode %}

{% code title="Person\_newgen.java" %}
```java
class Person
{
	// =============== Fields ===============

	String firstName;
	String lastName;

	int age;

	// =============== Methods ===============
}
```
{% endcode %}

The result of the `diff3` command is now:

{% code title="Person\_merged.java" %}
```java
class Person
{
	// =============== Fields ===============

	String firstName;
	String lastName;

	int age;

	// =============== Methods ===============

	String getFullName()
	{
		return firstName + " " + lastName;
	}
}
```
{% endcode %}

This solution does not always work, however. If we actually do generated the getters and setters, the `getAge` method will conflict with `getFullName` even with the separators:

```text
class Person
{
    private String firstName;
    private String lastName;
    private int age;

    public String getFirstName()
    {
        return this.firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }
<<<<<<< Person_edited.java

    public String getFullName()
    {
        return firstName + " " + lastName;
    }
||||||| Person.java
=======

    public int getAge()
    {
        return this.age;
    }
>>>>>>> Person_newgen.java
}
```



