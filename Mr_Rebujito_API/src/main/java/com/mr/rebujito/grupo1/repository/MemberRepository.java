package com.mr.rebujito.grupo1.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.mr.rebujito.grupo1.entity.Member;

@Repository
public interface MemberRepository extends CrudRepository<Member, Integer> {
	Optional<Member> findByUsername(String username);
}
