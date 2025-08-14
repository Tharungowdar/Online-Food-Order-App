package orderapp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import orderapp.entity.User;

public interface UserService {
	
	 User createUser(User user);
	
	User getUser(Integer id);
	
	List<User> getAllUser();
	
	User updateUser(User user,Integer id);
	
	void delete(Integer id);
	
	String uploadImage(MultipartFile file,Integer id) throws IOException;
	
	byte[] getImage(Integer id);

}
