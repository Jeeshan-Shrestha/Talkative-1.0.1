package com.chatapp.ChatAppV2.Controllers;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chatapp.ChatAppV2.Models.BackendResponse;
import com.chatapp.ChatAppV2.Models.PostDTO;
import com.chatapp.ChatAppV2.Services.PostService;
import com.mongodb.client.gridfs.model.GridFSFile;

import io.jsonwebtoken.io.IOException;



@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @GetMapping("/{username}")
    public ResponseEntity<?> getPostsFromUser(@PathVariable String username){
        try{
            List<PostDTO> postsFromUser = postService.getPostsFromUser(username);
            return ResponseEntity.ok().body(new BackendResponse(true,postsFromUser));
        }catch(UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BackendResponse(false,e.getMessage()));
        }
         catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BackendResponse(false,"something went wrong"));
        }
    }

    @GetMapping
    public ResponseEntity<?> getSelfPost(){
        try{
            List<PostDTO> postsFromUser = postService.getSelfPost();
            return ResponseEntity.ok().body(new BackendResponse(true,postsFromUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BackendResponse(false,"something went wrong"));
        }
    }

    @PostMapping
    public ResponseEntity<?> postThePost(@RequestPart("caption") String caption, @RequestPart("image") MultipartFile file) {
        try{
            postService.postThePost(caption,file);
            return ResponseEntity.ok().body(new BackendResponse(true, "Your Post has been posted"));
        }catch(IOException e){
            return ResponseEntity.badRequest().body(new BackendResponse(false, e.getMessage()));
        }
        catch(java.io.IOException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BackendResponse(false,"something went wrong"));
        }     
    }

    @GetMapping("/image/{imageId}")
    public ResponseEntity<?> getImage(@PathVariable String imageId) throws IOException, java.io.IOException {
    
    ObjectId fileId = new ObjectId(imageId);

    GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
    if (file == null) {
        return ResponseEntity.notFound().build();
    }
    GridFsResource resource = gridFsTemplate.getResource(file);

    return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(file.getMetadata().get("_contentType").toString()))
            .body(resource.getInputStream().readAllBytes());
}

    @GetMapping("/like")
    public ResponseEntity<?> likePost(@RequestParam String id, @RequestParam String likedUsername) {
        try{
            String message = postService.likePost(id, likedUsername);
            return ResponseEntity.ok().body(new BackendResponse(true, message));
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BackendResponse(false,e.getMessage()));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BackendResponse(false,"something went wrong"));
        }
    }
    
    

}
