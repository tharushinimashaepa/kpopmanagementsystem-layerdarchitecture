Drop database KpopManagementSystem;
create database KpopManagementSystem;
use KpopManagementSystem;

Create table User(
                     U_id varchar(5) primary key,
                     Name varchar(20)
);

create table Employee(
                         EM_id varchar(6) PRIMARY KEY,
                         Name varchar(20),
                         DOB date,
                         Address varchar(18),
                         U_id varchar(5),
                         CONSTRAINT FOREIGN KEY( U_id) REFERENCES User(U_id) on Delete Cascade on Update Cascade
);


create table Positions(
                          P_id varchar(6) PRIMARY KEY,
                          Title varchar(20)
);

create table  Idol_Group(
                            I_id varchar(6) PRIMARY KEY,
                            name varchar(20),
                            member_count int(2),
                            fandom_name varchar(18),
                            label_name varchar(20),
                            U_id varchar(5),

                            CONSTRAINT FOREIGN KEY( U_id) REFERENCES User(U_id) on Delete Cascade on Update Cascade
);

create table  Music_Producer(
                                MP_id varchar(6) PRIMARY KEY,
                                name varchar(20),
                                work varchar(15)
);


create table Music(
                      M_id varchar(6) PRIMARY KEY,
                      Group_name varchar(20),
                      title varchar(15),
                      genre varchar(15)
);


create table Collabararion_details(
                                      I_id varchar(6) ,
                                      collabaration varchar(20),
                                      MP_id varchar(6),
                                      CONSTRAINT FOREIGN KEY (I_id ) REFERENCES Idol_Group(I_id ) on Delete Cascade on Update Cascade,
                                      CONSTRAINT FOREIGN KEY ( MP_id ) REFERENCES  Music_Producer(MP_id) on Delete Cascade on Update Cascade
);

create table Release_details(
                                M_id varchar(6) ,
                                date date,
                                MP_id varchar(6),
                                CONSTRAINT FOREIGN KEY (M_id ) REFERENCES Music(M_id ) on Delete Cascade on Update Cascade,
                                CONSTRAINT FOREIGN KEY ( MP_id ) REFERENCES  Music_Producer(MP_id) on Delete Cascade on Update Cascade
);

create table  Event(
                       EV_id varchar(6) PRIMARY KEY,
                       name varchar(20),
                       genre_of_event varchar(2),
                       venue varchar(18),
                       price int(6),
                       EM_id varchar(5),

                       CONSTRAINT FOREIGN KEY( EM_id) REFERENCES Employee(EM_id) on Delete Cascade on Update Cascade
);



create table  Merchandise(
                             MR_id varchar(6) PRIMARY KEY,
                             name varchar(20),
                             qty_on_hand varchar(2),
                             price int(6)
);

create table Participate_details(
                                    EV_id varchar(6) ,
                                    performence varchar(20),
                                    I_id varchar(6),
                                    CONSTRAINT FOREIGN KEY (I_id ) REFERENCES Idol_Group(I_id ) on Delete Cascade on Update Cascade,
                                    CONSTRAINT FOREIGN KEY ( EV_id ) REFERENCES  Event(EV_id) on Delete Cascade on Update Cascade
);

create table Fans_club(
                          F_id varchar(6) PRIMARY KEY,
                          fanclub_name varchar(20),
                          I_id varchar(6),
                          CONSTRAINT FOREIGN KEY (I_id ) REFERENCES Idol_Group(I_id ) on Delete Cascade on Update Cascade
);


create table Social_media(
                             SM_id varchar(6) PRIMARY KEY,
                             profile_name varchar(20),
                             password varchar(6)
);
