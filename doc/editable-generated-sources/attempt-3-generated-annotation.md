# Attempt 3 – @Generated annotation

{% code title="Person.java" %}
```java
class Person
{
	@Generated
	private String firstName;
	@Generated
	private String lastName;

	@Generated
	public String getFirstName()
	{
		return this.firstName;
	}

	@Generated
	public String getLastName()
	{
		return this.lastName;
	}
}
```
{% endcode %}

{% code title="Person\_edited.java" %}
```java
class Person
{
	@Generated
	private String firstName;
	@Generated
	private String lastName;

	public String getFirstName()
	{
		return this.firstName == null ? "" : this.firstName;
	}

	public String getLastName()
	{
		return this.lastName + " Jr";
	}

	public String getFullName()
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
	@Generated
	private String firstName;
	@Generated
	private String lastName;
	@Generated
	private int age;

	@Generated
	public String getFirstName()
	{
		return this.firstName;
	}

	@Generated
	public String getLastName()
	{
		return this.lastName;
	}

	@Generated
	public int getAge()
	{
		return this.age;
	}
}
```
{% endcode %}

{% code title="Merged.java" %}
```java
class Person
{
    @Generated
    private String firstName;
    @Generated
    private String lastName;
    @Generated
    private int age;

    public String getFirstName()
    {
        return this.firstName == null ? "" : this.firstName;
    }

    public String getLastName()
    {
        return this.lastName + " Jr";
    }

    public String getFullName()
    {
        return firstName + " " + lastName;
    }

    @Generated
    public int getAge()
    {
        return this.age;
    }
}
```
{% endcode %}

Also works when removing generated methods:

{% code title="Person\_edited.java" %}
```java
class Person
{
	@Generated
	private String firstName;
	@Generated
	private String lastName;

	@Generated
	public String getFirstName()
	{
		return this.firstName;
	}

	public String getFullName()
	{
		return firstName + " " + lastName;
	}
}
```
{% endcode %}

{% code title="Person\_merged.java" %}
```java
class Person
{
	@Generated
	private String firstName;
	@Generated
	private String lastName;
	@Generated
	private int age;

	@Generated
	public String getFirstName()
	{
		return this.firstName;
	}

	public String getFullName()
	{
		return firstName + " " + lastName;
	}

	@Generated
	public int getAge()
	{
		return this.age;
	}
}
```
{% endcode %}

