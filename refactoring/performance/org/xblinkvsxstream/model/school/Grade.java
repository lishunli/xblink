package org.xblinkvsxstream.model.school;

import org.xblink.api.annotations.XBlinkAlias;
import org.xblink.api.annotations.XBlinkAsArray;
import org.xblink.api.annotations.XBlinkAsAttribute;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XBlinkAlias("grade")
@XStreamAlias("grade")
public class Grade {

	@XBlinkAsArray
	private Student[] students;

	@XBlinkAsAttribute
	@XStreamAsAttribute
	private long level;

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public Student[] getStudents() {
		return students;
	}

	public void setStudents(Student[] students) {
		this.students = students;
	}

}
