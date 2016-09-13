package com.mdsgpp.cidadedemocratica.persistence;

import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.model.User;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 9/9/16.
 */
public class DataContainer {

    private ArrayList<Tag> tags = new ArrayList<Tag>();
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Proposal> proposals = new ArrayList<Proposal>();
    private DataUpdateListener dataUpdateListener = new DataUpdateListener() {
        @Override
        public void tagsUpdated() {

        }

        @Override
        public void proposalsUpdated() {

        }

        @Override
        public void usersUpdated() {

        }
    };

    private static DataContainer instance;

    private DataContainer() {

    }

    public static DataContainer getInstance() {
        if (DataContainer.instance == null) {
            DataContainer.instance = new DataContainer();
        }

        return DataContainer.instance;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Proposal> getProposals() {
        return proposals;
    }

    public void setDataUpdateListener(DataUpdateListener dataUpdateListener) {
        this.dataUpdateListener = dataUpdateListener;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
        this.dataUpdateListener.tagsUpdated();
    }

    public void addUser(User user) {
        this.users.add(user);
        this.dataUpdateListener.usersUpdated();
    }

    public void addProposal(Proposal proposal) {
        this.proposals.add(proposal);
        this.dataUpdateListener.proposalsUpdated();
    }

    public void addTags(ArrayList<Tag> tags) {
        this.tags.addAll(tags);
        this.dataUpdateListener.tagsUpdated();
    }

    public void addUsers(ArrayList<User> users) {
        this.users.addAll(users);
        this.dataUpdateListener.usersUpdated();
    }

    public void addProposals(ArrayList<Proposal> proposals) {
        this.proposals.addAll(proposals);
        this.dataUpdateListener.proposalsUpdated();
    }
}
