# Attempt 2 – GENERATED Separator Comments

The solution is to introduce separators not for kinds of declarations, but for generated code \(only the merge output is shown below\):

```text
class Person
{
    /////////////// BEGIN GENERATED ///////////////
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

    public int getAge()
    {
        return this.age;
    }
    /////////////// END GENERATED ///////////////

    public String getFullName()
    {
        return firstName + " " + lastName;
    }
}
```

Changes to the code within the generated section are still preserved:

{% code title="Person\_edited.java" %}
```java
class Person
{
	/////////////// BEGIN GENERATED ///////////////
	private String firstName;
	private String lastName;

	public String getFirstName()
	{
		return this.firstName == null ? "" : this.firstName;
	}

	public String getLastName()
	{
		return this.lastName + " Jr";
	}
	/////////////// END GENERATED ///////////////

	public String getFullName()
	{
		return firstName + " " + lastName;
	}
}
```
{% endcode %}

