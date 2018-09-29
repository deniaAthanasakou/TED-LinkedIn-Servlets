package JavaFiles;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import database.entities.User;

@JacksonXmlRootElement(localName = "Users")
public class Wrapper {
	@JacksonXmlProperty(localName = "User")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}