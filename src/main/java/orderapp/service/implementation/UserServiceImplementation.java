package orderapp.service.implementation;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import orderapp.entity.User;
import orderapp.repositery.UserRepository;
import orderapp.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

	@Autowired
	private final UserRepository userRepository;

	@Override
	public User createUser(User user) {

		return userRepository.save(user);
	}

	@Override
	public User getUser(Integer id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new NoSuchElementException("User with id " + id + "not found");
		}
	}

	@Override
	@Cacheable(value = "user_cache")
	public List<User> getAllUser() {
		List<User> users = userRepository.findAll();
		if (users.isEmpty()) {
			throw new NoSuchElementException("User not found");
		} else {
			return users;
		}
	}

	@Override
	@CachePut(value = "user_cache", key = "#id")
	public User updateUser(User user, Integer id) {
		User existing = getUser(id);
		existing.setName(user.getName());
		existing.setAddress(user.getAddress());
		existing.setContactNumber(user.getContactNumber());
		existing.setEmail(user.getEmail());
		existing.setGender(user.getGender());
		existing.setPassword(user.getPassword());

		return createUser(user);
	}

	@Override
	@CacheEvict(value = "user_cache", key = "#id")
	public void delete(Integer id) {
		User user = getUser(id);
		userRepository.delete(user);
	}
	
	@CacheEvict(value = "user_cache", allEntries = true)
	@Scheduled(fixedRate = 120000)//time in milliseconds
	public void evictAllCache() {
		System.out.println("Evacuting all the cache from user Cache");
	}

	@Override
	public String uploadImage(MultipartFile file, Integer id) throws IOException {
		byte[] image = file.getBytes();
		User user = getUser(id);
		user.setImage(image);
		userRepository.save(user);
		return "Image Uploded";
	}

	@Override
	public byte[] getImage(Integer id) {
		User user = getUser(id);
		byte[] image = user.getImage();
		if (image.length == 0 || image == null) {
			throw new NoSuchElementException("USer with the id " + id + "not uploded the image");
		} else {
			return image;
		}
	}

}
