package com.mr.rebujito.grupo1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mr.rebujito.grupo1.entity.Admin;
import com.mr.rebujito.grupo1.entity.Member;
import com.mr.rebujito.grupo1.entity.Partner;
import com.mr.rebujito.grupo1.entity.Stand;
import com.mr.rebujito.grupo1.entity.TownHall;
import com.mr.rebujito.grupo1.repository.AdminRepository;
import com.mr.rebujito.grupo1.repository.MemberRepository;
import com.mr.rebujito.grupo1.repository.PartnerRepository;
import com.mr.rebujito.grupo1.repository.StandRepository;
import com.mr.rebujito.grupo1.repository.TownHallRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class MemberService implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private PartnerRepository partnerRepository;
	
	@Autowired
	private StandRepository standRepository;
	
	@Autowired
	private TownHallRepository townHallRepository;

	public Optional<Member> findByUsername(String username) {
		return this.memberRepository.findByUsername(username);
	}

	public void save(Member member) {
		this.memberRepository.save(member);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> usuario = memberRepository.findByUsername(username);

		if (usuario.isEmpty()) {
			throw new UsernameNotFoundException("Member not found");
		}

		Member member = usuario.get();
		List<GrantedAuthority> autoridades = new ArrayList<GrantedAuthority>();
		autoridades.add(new SimpleGrantedAuthority(member.getRol().toString()));

		return new User(member.getUsername(), member.getPassword(), autoridades);
	}

	public <T> T memberLogin() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		T res = null;

		if (StringUtils.isNotBlank(username)) {
			Optional<Member> m = memberRepository.findByUsername(username);
			if (m.isPresent()) {
				Member member = m.get();
				switch (member.getRol()) {
				case ADMIN:
					Optional<Admin> adminOptional = adminRepository.findByUsername(username);
					if (adminOptional.isPresent()) {
						res = (T) adminOptional.get();
					}
					break;
				case PARTNER:
					Optional<Partner> partnerOptional = partnerRepository.findByUsername(username);
					if (partnerOptional.isPresent()) {
						res = (T) partnerOptional.get();
					}
					break;
				case STAND:
					Optional<Stand> standOptional = standRepository.findByUsername(username);
					if (standOptional.isPresent()) {
						res = (T) standOptional.get();
					}
					break;
				case TOWNHALL:
					Optional<TownHall> townHallOptional = townHallRepository.findByUsername(username);
					if (townHallOptional.isPresent()) {
						res = (T) townHallOptional.get();
					}
					break;
				}
			}
		}
		return res;
	}
}
