package com.example.sprintproject.fragments.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.TravelPost;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class TravelCommunityViewModel extends ViewModel {

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final MutableLiveData<List<TravelPost>> posts = new MutableLiveData<>();

    public LiveData<List<TravelPost>> getPosts() {
        return posts;
    }

    public void loadPosts() {
        firestore.collection("community_posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((querySnapshot, e) -> {
                    if (e != null) {
                        Log.e("TravelCommunity", "Error loading posts: " + e.getMessage());
                        return;
                    }

                    if (querySnapshot != null) {
                        List<TravelPost> postList = new ArrayList<>();
                        querySnapshot.forEach(doc -> postList.add(doc.toObject(TravelPost.class)));
                        posts.postValue(postList);
                    }
                });
    }

    public void addTravelPost(TravelPost post, com.google.android.gms.tasks.OnCompleteListener<DocumentReference> listener) {
        firestore.collection("community_posts")
                .add(post)
                .addOnCompleteListener(listener)
                .addOnFailureListener(
                        e -> Log.e("TravelCommunity", "Failed to add post: " + e.getMessage()));
    }
}
