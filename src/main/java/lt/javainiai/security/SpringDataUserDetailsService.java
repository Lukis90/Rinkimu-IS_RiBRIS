//package lt.javainiai.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SpringDataUserDetailsService implements UserDetailsService {
//
//	private final AdminRepository repository;
//
//	@Autowired
//	public SpringDataUserDetailsService(AdminRepository repository) {
//		this.repository = repository;
//	}
//
//	@Override
//	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//		Admin admin = repository.findByName(name);
//		return new User(admin.getName(), admin.getPassword(),
//				AuthorityUtils.createAuthorityList(admin.getRoles()));
//	}
//
//}
