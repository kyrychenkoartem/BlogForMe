package com.kyrychenko.BlogForMe.repos;

import com.kyrychenko.BlogForMe.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

}
