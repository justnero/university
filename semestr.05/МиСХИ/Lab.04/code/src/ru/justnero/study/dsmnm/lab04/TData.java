package ru.justnero.study.dsmnm.lab04;

import java.io.PrintStream;
import java.util.Scanner;

class TData implements Comparable<TData> {

    private String name;
    private long phone;
    private String home;
    private float account;

    public TData(long phone) {
        this.phone = phone;
    }

    TData(String name, long phone, String home, float account) {
        this.name = name;
        this.phone = phone;
        this.home = home;
        this.account = account;
    }

    static TData read(Scanner inp) {
        String name = inp.next();
        name = name.replaceAll(",", ", ")
                .replaceAll("_", " ")
                .replaceAll("\"\"", "\"");
        if (name.startsWith("\"") && name.endsWith("\"")) {
            name = name.substring(1, name.length() - 1);
        }
        long phone = inp.nextLong();
        String home = inp.next();
        float account = inp.nextFloat();
        return new TData(name, phone, home, account);
    }

    public void write(PrintStream out) {
        out.print(name);
        out.print(" ");
        out.print(phone);
        out.print(" ");
        out.print(home);
        out.print(" ");
        out.print(account);
        out.println();
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    float getAccount() {
        return account;
    }

    public void setAccount(float account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return String.valueOf(phone);
    }

    @Override
    public int compareTo(TData to) {
        return phone == to.phone ? 0 : (phone > to.phone ? 1 : -1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TData tData = (TData) o;

        return phone == tData.phone;

    }

    @Override
    public int hashCode() {
        return (int) (phone ^ (phone >>> 32));
    }
}
