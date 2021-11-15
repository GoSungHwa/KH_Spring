package com.mycompany.myapp07.model.domain;

public class Sample {
	private String name;
	private int age;

	@Override
	public String toString() {
		return "Sample [name=" + name + ", age=" + age + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
}
