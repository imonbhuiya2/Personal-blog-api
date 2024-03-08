package com.onneshon.blog.servicesImple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.onneshon.blog.entities.Blog;
import com.onneshon.blog.entities.Category;
import com.onneshon.blog.entities.Comment;
import com.onneshon.blog.entities.User;
import com.onneshon.blog.exceptions.ResourceNotFoundException;
import com.onneshon.blog.payloads.BlogDto;
import com.onneshon.blog.payloads.CommentDto;
import com.onneshon.blog.payloads.PageResponse;
import com.onneshon.blog.repositories.BlogRepo;
import com.onneshon.blog.repositories.CategoryRepo;
import com.onneshon.blog.repositories.UserRepo;
import com.onneshon.blog.services.BlogServices;
import com.onneshon.blog.services.CommentServices;

@Service
public class BlogServicesImple implements BlogServices {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private BlogRepo blogRepo;

	@Autowired
	private CommentServices commentServices;

	@Autowired
	private CategoryServiceImple catService;

	@Autowired
	private UserServicesImple userService;

	@Override
	public BlogDto addBlog(BlogDto blogDto, int userId) {

		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		Category category = categoryRepo.findById(blogDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", blogDto.getCategoryId()));


		Blog blog = this.blogDtoToBlog(blogDto);
		blog.setAddedDate(new Date());
		blog.setUser(user);
		blog.setCategory(category);


		Blog savedBlog = blogRepo.save(blog);

		return this.blogToBlogDto(savedBlog);
	}

	// update blog
	@Override
	public BlogDto updateBlog(BlogDto blogDto, int blogId) {
		// user, category thik ache ki na check kora hocche
		Blog blog = blogRepo.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException("Blog", "BlogId", blogId));
//		Category category = categoryRepo.findById(blogDto.getCategoryId()).orElseThrow(()-> new ResourceNotFoundException("Category", "category id", blogDto.getCategoryId()));

		UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String loggedInUserEmail = principal.getUsername();
		String blogUser = blog.getUser().getEmail();

		// jodi Logged in user r blog er user match na kore taile update kora jabe na
		if (!loggedInUserEmail.equals(blogUser)) {
			BlogDto blogDto2 = null;
			return blogDto2;
		}

		blog.setBlogTitle(blogDto.getBlogTitle());
		blog.setBlogContent(blogDto.getBlogContent());
		blog.setBlogImage(blog.getBlogImage());

		Blog savedBlog = blogRepo.save(blog);

		return this.blogToBlogDto(savedBlog);
	}

	// deleteblog
	@Override
	public void deleteBlog(int blogId) {
		Blog blog = blogRepo.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException("Blog", "BlogId", blogId));
		blogRepo.delete(blog);
	}

	// get blog by id
	@Override
	public BlogDto getBlogById(int blogId) {
		Blog blog = blogRepo.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException("Blog", "BlogId", blogId));

		List<CommentDto> commentsDto = commentServices.getCommentsForBlog(blogToBlogDto(blog));

		CommentServicesImple csi = new CommentServicesImple();
		List<Comment> comments = new ArrayList<>();

		for (CommentDto com : commentsDto) {
			comments.add(csi.commentDtoToComment(com));
		}
		blog.setComments(comments);

		return this.blogToBlogDto(blog);
	}

	// get all blogs
	@Override
	public PageResponse getAllBlogs(int pageNumber, int pageSize, String sortBy, String sortDirection) {

		Sort sort = null;
		if (sortDirection.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}

		// STEP pagable
		Page<Blog> pageInfo;
		try {
			Pageable page = PageRequest.of(pageNumber, pageSize, sort);
			pageInfo = blogRepo.findAll(page);
		} catch (Exception e) {
			// jodi sort by er value incorrect hoy tahole eta cholbe
			Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
			pageInfo = blogRepo.findAll(page);
		}

		List<Blog> allBlogs = pageInfo.getContent();

		List<BlogDto> blogs = new ArrayList<>();

		for (Blog blog : allBlogs) {

			// getting all comments for the blogs
			List<CommentDto> commentsDto = commentServices.getCommentsForBlog(blogToBlogDto(blog));
			CommentServicesImple csi = new CommentServicesImple();
			List<Comment> comments = new ArrayList<>();

			for (CommentDto com : commentsDto) {
				comments.add(csi.commentDtoToComment(com));
			}
			blog.setComments(comments);
			// comment ends

			blogs.add(this.blogToBlogDto(blog));
		}

		PageResponse pageData = new PageResponse();
		pageData.setContent(blogs);
		pageData.setPageNumber(pageInfo.getNumber());
		pageData.setPageSize(pageInfo.getSize());
		pageData.setTotalElements(pageInfo.getTotalElements());
		pageData.setTotalPages(pageInfo.getTotalPages());
		pageData.setNumberOfElements(pageInfo.getNumberOfElements());

		pageData.setEmpty(pageInfo.isEmpty());
		pageData.setFirst(pageInfo.isFirst());
		pageData.setLast(pageInfo.isLast());

		return pageData;
	}

	// get blogs by user id
	@Override
	public PageResponse getAllBlogsByUser(int userId, int pageNumber, int pageSize, String sortBy,
			String sortDirection) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Sort sort = null;
		if (sortDirection.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}

		// STEP pagable
		Page<Blog> pageInfo;
		try {
			Pageable page = PageRequest.of(pageNumber, pageSize, sort);
			pageInfo = blogRepo.findByUser(user, page);
		} catch (Exception e) {
			// jodi sort by er value incorrect hoy tahole eta cholbe
			Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
			pageInfo = blogRepo.findByUser(user, page);
		}

		List<Blog> allBlogs = pageInfo.getContent();

		List<BlogDto> blogs = new ArrayList<>();
		for (Blog blog : allBlogs) {
			// getting all comments for the blogs
			List<CommentDto> commentsDto = commentServices.getCommentsForBlog(blogToBlogDto(blog));
			CommentServicesImple csi = new CommentServicesImple();
			List<Comment> comments = new ArrayList<>();

			for (CommentDto com : commentsDto) {
				comments.add(csi.commentDtoToComment(com));
			}
			blog.setComments(comments);
			// comment ends

			blogs.add(this.blogToBlogDto(blog));
		}

		PageResponse pageData = new PageResponse();
		pageData.setContent(blogs);
		pageData.setPageNumber(pageInfo.getNumber());
		pageData.setPageSize(pageInfo.getSize());
		pageData.setTotalElements(pageInfo.getTotalElements());
		pageData.setTotalPages(pageInfo.getTotalPages());
		pageData.setNumberOfElements(pageInfo.getNumberOfElements());

		pageData.setEmpty(pageInfo.isEmpty());
		pageData.setFirst(pageInfo.isFirst());
		pageData.setLast(pageInfo.isLast());

		return pageData;
	}

	// get blogs by category id
	@Override
	public PageResponse getAllBlogsByCategory(int catId, int pageNumber, int pageSize, String sortBy,
			String sortDirection) {
		Category category = categoryRepo.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", catId));

		Sort sort = null;
		if (sortDirection.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}

		// STEP pagable
		Page<Blog> pageInfo;
		try {
			Pageable page = PageRequest.of(pageNumber, pageSize, sort);
			pageInfo = blogRepo.findByCategory(category, page);
		} catch (Exception e) {
			// jodi sort by er value incorrect hoy tahole eta cholbe
			Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
			pageInfo = blogRepo.findByCategory(category, page);
		}

		List<Blog> allBlogs = pageInfo.getContent();

		List<BlogDto> blogs = new ArrayList<>();
		for (Blog blog : allBlogs) {
			blogs.add(this.blogToBlogDto(blog));
		}

		PageResponse pageData = new PageResponse();
		pageData.setContent(blogs);
		pageData.setPageNumber(pageInfo.getNumber());
		pageData.setPageSize(pageInfo.getSize());
		pageData.setTotalElements(pageInfo.getTotalElements());
		pageData.setTotalPages(pageInfo.getTotalPages());
		pageData.setNumberOfElements(pageInfo.getNumberOfElements());

		pageData.setEmpty(pageInfo.isEmpty());
		pageData.setFirst(pageInfo.isFirst());
		pageData.setLast(pageInfo.isLast());

		return pageData;
	}

	// search blogs
	@Override
	public PageResponse searchBlogs(String search_query, int pageNumber, int pageSize, String sortBy,
			String sortDirection) {

		Sort sort = null;
		if (sortDirection.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}

		// if search query te kono value na ashe taile kono kichu jeno show na kore
		if (search_query.isEmpty()) {
			search_query = null;
		}

		Pageable page = PageRequest.of(pageNumber, pageSize, sort);
		Page<Blog> pageInfo = blogRepo.findByBlogTitleContaining(search_query, page);

		List<Blog> allBlogs = pageInfo.getContent();

		
		List<BlogDto> blogs = new ArrayList<>();
		for (Blog blog : allBlogs) {

			// getting all comments for the blogs
			List<CommentDto> commentsDto = commentServices.getCommentsForBlog(blogToBlogDto(blog));
			CommentServicesImple csi = new CommentServicesImple();
			List<Comment> comments = new ArrayList<>();

			for (CommentDto com : commentsDto) {
				comments.add(csi.commentDtoToComment(com));
			}
			blog.setComments(comments);
			// comment ends

			blogs.add(this.blogToBlogDto(blog));
		}
		

		PageResponse pageData = new PageResponse();
		pageData.setContent(blogs);
		pageData.setPageNumber(pageInfo.getNumber());
		pageData.setPageSize(pageInfo.getSize());
		pageData.setTotalElements(pageInfo.getTotalElements());
		pageData.setTotalPages(pageInfo.getTotalPages());
		pageData.setNumberOfElements(pageInfo.getNumberOfElements());

		pageData.setEmpty(pageInfo.isEmpty());
		pageData.setFirst(pageInfo.isFirst());
		pageData.setLast(pageInfo.isLast());

		return pageData;
	}

	// DTO conversion
	public Blog blogDtoToBlog(BlogDto blogDto) {
		Blog blog = new Blog();

		blog.setId(blogDto.getId());
		blog.setBlogTitle(blogDto.getBlogTitle());
		blog.setBlogContent(blogDto.getBlogContent());
		blog.setBlogImage(blogDto.getBlogImage());
		blog.setAddedDate(blogDto.getAddedDate());

//		blog.setCategory(this.catService.catDtoTocat(blogDto.getCategory()));
//		blog.setUser(this.userService.userDtoToUser(blogDto.getUser()));

		return blog;

	}

	public BlogDto blogToBlogDto(Blog blog) {
		BlogDto blogDto = new BlogDto();

		blogDto.setId(blog.getId());
		blogDto.setBlogTitle(blog.getBlogTitle());
		blogDto.setBlogContent(blog.getBlogContent());
		blogDto.setBlogImage(blog.getBlogImage());
		blogDto.setAddedDate(blog.getAddedDate());
		blogDto.setCategoryId(blog.getCategory().getCategoryId());

		blogDto.setCategory(this.catService.catTocatDto(blog.getCategory()));
		blogDto.setUser(this.userService.userToUserDtoSecure(blog.getUser()));

		blogDto.setComments(blog.getComments());

		return blogDto;

	}

}
