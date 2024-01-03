//Name : William Baldwin
// Computing ID: vvp4cx@virginia.edu
// Homework Name: HW8 - Recursion
//Resources used: none

import java.util.Random;

public class Password {
    private String newPass;
    private String currPass;
    public Password() {
        newPass = "";
        currPass = "";
    }

    public Password(String password) {
        if (checkValid(password)) {
            currPass = password;
        } else {
            currPass = "";
        }
        newPass = "";
    }

    public String getPassword() {
        return currPass;
    }

    public boolean setPassword(String password) {
        this.newPass = password;
        if (changePassword(password)) {
            return true;
        } else {
            this.newPass = "";
            return false;
        }
    }
    public boolean checkValid(String password) {
        return hasDigit(password, password.length() - 1);
    }

    private boolean hasDigit(String password, int last) {
        if (last < 0) {
            return false;
        }
        if (Character.isDigit(password.charAt(last))) {
            return true;
        } else {
            return hasDigit(password, last - 1);
        }
    }

    //calls recursive method to check if a new password is equal to the old one after checking for
    //equal lengths
    private boolean samePassCheck() {
        if (newPass.length() > currPass.length() || newPass.length() < currPass.length()) {
            return false;
        }
        return charLoop(newPass.length() - 1);
    }

    //recursive method for checking keeps calling itself as long as chars are equal, once it gets
    //past the first letter it will return true, if it finds one not equal returns false
    private boolean charLoop(int last) {
        if (last < 0) {
            return true;
        }
        if (newPass.charAt(last) == currPass.charAt(last)) {
            return charLoop(last - 1);
        } else {
            return false;
        }
    }

    //Checks how many differences between two passwords, before passing to recursive method part
    //it finds the shared length and then uses the difference in length to add on answer at the end
    //passes 0 to start count at 0 in recursive method for number of differences
    private int numDiff() {
        int min = 0;
        int diff = 0;
        if(currPass.length() <= newPass.length()) {
            min = currPass.length() - 1;
            diff = newPass.length() - currPass.length();
        } else {
            min = newPass.length() - 1;
            diff = currPass.length() - newPass.length();
        }

        return numDiffRecursive(min, 0) + diff;
    }

    //part of numDiff which is recursive, takes the two passwords and cycles through them starting at
    //min which is their shared length and counts how many chars are different each time
    //once it reaches past the first letter it returns the count
    private int numDiffRecursive(int min, int count) {
        if (min < 0) {
            return count;
        }
        if (currPass.charAt(min) == newPass.charAt(min)) {
            return numDiffRecursive(min - 1, count);
        } else {
            return numDiffRecursive(min - 1, count + 1);
        }
    }

    //just passes the password in with the current password and checks if the numdiff
    //returns the minimum amount or greater
    private boolean suffDiff(int minDiff) {
        return numDiff() >= minDiff;
    }

    public boolean changePassword(String password) {
        if (!checkValid(password) || samePassCheck()) {
            return false;
        }

        int min = currPass.length() / 2;

        if (!suffDiff(min)) return false;

        this.currPass = newPass;
        return true;
    }

    public String toString() {
        if (currPass.length() < 2) {
            return "Current Password: **";
        }
        Random rand = new Random();
        int numAst = rand.nextInt(20) + 1;
        String ast = "";
        for (int i = 0; i < numAst; i++) {
            ast += "*";
        }
        return currPass.charAt(0) + ast + currPass.charAt(currPass.length() - 1);
    }

    public boolean equals (Password other) {
        if (this == other) // if both references point to same object, it equals itself
            return true;
        if (other == null) // an object never equal to null (no object)
            return false;
        return currPass.equals(other.currPass);
    }











}
