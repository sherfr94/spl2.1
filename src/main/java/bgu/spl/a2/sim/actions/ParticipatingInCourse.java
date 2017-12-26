package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import bgu.spl.a2.sim.subActions.SubParticipatingInCourse;

public class ParticipatingInCourse extends Action<String> {

    private String studentID;
    private String grade;

    public ParticipatingInCourse(String studentID, String grade) {
        this.studentID = studentID;
        this.grade = grade;
    }

    @Override
    protected void start() {

//        System.out.println("#########1");
//
//        CoursePrivateState privateState = ((CoursePrivateState) getPrivateState());
//
//        if (privateState.getAvailableSpots() == 0) {
//            complete("Participating fail: \tNo space left");
//            System.out.println(getResult().get());
//        }
//
//        else{
//            boolean problem = false;
//            HashMap<String, Integer> grades = ((StudentPrivateState)getPool().getPrivateState(studentID)).getGrades();
//            if (!(privateState.getPrequisites().isEmpty())) {
//                for (String course : privateState.getPrequisites()) {
//                    if (grades.get(course) == null) {
//                        problem = true;
//                        break;
//                    }
//                }
//
//                System.out.println("#########2");
//                if(problem){
//                    complete("Participating fail: \tNo prerequisites");
//                    System.out.println(getResult().get());
//                }
//
//                else{
//                    System.out.println("#########3");
//                    privateState.getRegStudents().add(studentID);
//                    privateState.setAvailableSpots(privateState.getAvailableSpots() - 1);
//                    privateState.setRegistered(privateState.getRegistered() + 1);
//
//                    Integer gradeInt= new Integer((-1));
//                    if(!grade.equals("-")){
//                        gradeInt = Integer.parseInt(grade);
//                    }
//
//                    ((StudentPrivateState)getPool().getPrivateState(studentID)).addGrade(getActorId(), gradeInt);
//
//
//                    System.out.println("#########4");
//                    complete("Participating success: \tStudent: " + studentID + " registered to course: " + getActorId());
//                    //System.out.println(privateState.getRegistered()); //TODO BUG: SPL 2 registered
//                    System.out.println(getResult().get());
//
//
//                }
//
//            }
//        }



        CoursePrivateState privateState = ((CoursePrivateState) getPrivateState());

        if (privateState.getAvailableSpots() <= 0) {
            complete("Participating fail: \tNo space left");
            System.out.println(getResult().get());
        }

        //enough space
        else {
           // System.out.println("YYY1"+getPool().getIsTaken().get(getActorId()));

                SubParticipatingInCourse subParticipatingInCourse = new SubParticipatingInCourse(grade, getActorId(), privateState.getPrequisites());

                //ArrayList<Action<?>> actions = new ArrayList<>();

                //actions.add(subParticipatingInCourse);
                getPool().submit(subParticipatingInCourse, studentID, new StudentPrivateState());
                Promise<Boolean> promise = subParticipatingInCourse.getResult();

                promise.subscribe(() -> {
                    //getPool().getIsTaken().get(getActorId()).compareAndSet(false,true);
                   // System.out.println("YYY2"+getPool().getIsTaken().get(getActorId()));
                    Boolean result = subParticipatingInCourse.getResult().get();
                    //prerequisits ok
                    if (result) {//TODO: BUG: nullPointerExeption

                        privateState.getRegStudents().add(studentID);


                        privateState.setAvailableSpots(privateState.getAvailableSpots() - 1);
                        privateState.setRegistered(privateState.getRegistered() + 1);
                        complete("Participating success: \tStudent: " + studentID + " registered to course: " + getActorId());
                        System.out.println(privateState.getRegistered()); //TODO BUG: SPL 2 registered
                        System.out.println(getResult().get());
                    }
                    //prerequisites not ok
                    else {
                        complete("Participating fail: \tNo prerequisites");
                        System.out.println(getResult().get());
                    }
                });

            }
        }


}




