package controllers;

import play.*;
import play.data.*;
import play.db.DB;
import play.mvc.*;
import views.html.*;
import models.*;
import play.mvc.Http.*;

//import play.data.FormFactory;

import java.sql.*;
import java.util.*;

import com.google.gson.Gson;

import db.DataAccess;
import frames.CRFReader;

public class Secured extends Security.Authenticator{
    
    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("username");
    }
    
    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.Application.login());
    }
    
    
    
    
}