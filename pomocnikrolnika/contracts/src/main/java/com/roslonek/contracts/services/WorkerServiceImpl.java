package com.roslonek.contracts.services;

import com.roslonek.contracts.entities.UserDetails;
import com.roslonek.contracts.entities.Worker;
import com.roslonek.contracts.repositories.UserDetailsRepository;
import com.roslonek.contracts.repositories.WorkerRepository;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Service
public class WorkerServiceImpl implements WorkerService {


    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Override
    public void deactivateAllWorkers(long idi) {

        List<Worker> workers = workerRepository.findWorkers(idi, true);

        for (Worker worker : workers) {
            worker.setActive(false);
            workerRepository.save(worker);
        }

    }

    @Override
    public List<Worker> searchWorker(long userId, String search) {
        List<Worker> workersSearch = workerRepository.findWorkers(userId, false);
        for (int i = workersSearch.size() - 1; i >= 0; i--) {
            if (workersSearch.get(i).getSurname().contains(search)) {
            } else {
                workersSearch.remove(i);
            }


        }
        return workersSearch;
    }

    @Override
    public void printAllBeginning(String startDate, String endDate, List<Worker> workers) {


        for (Worker worker : workers) {
            worker.setStartDate(startDate);
            workerRepository.save(worker);
            contracts(worker, startDate, endDate);

        }
    }


    public static void setSingleLineSpacing(XWPFParagraph para) {
        CTPPr ppr = para.getCTP().getPPr();
        if (ppr == null)
            ppr = para.getCTP().addNewPPr();
        CTSpacing spacing = ppr.isSetSpacing() ? ppr.getSpacing() : ppr.addNewSpacing();
        spacing.setAfter(BigInteger.valueOf(0));
        spacing.setBefore(BigInteger.valueOf(0));
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf(240));
    }

    public static String theMonth(int month) {
        String[] monthNames = {"Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"};
        return monthNames[month - 1];
    }

    @Override
    public void contracts(Worker worker, String startDate, String endDate) {

        UserDetails userDetails = userDetailsRepository.findByUserId(worker.getUserId());

        String name = userDetails.getName() + " " + userDetails.getSurname();
        String fullName = userDetails.getFullName();
        String nip = userDetails.getNip();
        String nazwa = worker.getName() + " " + worker.getSurname();
        String paszport = worker.getPassport();
        String adres = worker.getAdress();
        String start = startDate;
        String koniec = endDate;
        String wage = userDetails.getWage();

        XWPFDocument document = new XWPFDocument();
        FileOutputStream out = null;




        try {
            out = new FileOutputStream(
                    new File(worker.getId() + " - najem.docx"));
        } catch (FileNotFoundException e) {
            System.out.println("Nieodpowiednia ścieżka dla pliku");
            e.printStackTrace();
        }

        XWPFParagraph paragraph = document.createParagraph();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setFontSize(11);
        run.setText("UMOWA UŻYCZENIA LOKALU");
        run.addBreak();
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run.setBold(false);
        run.setText("Zawarta dnia " + start + " r. w miejscowości Zawady pomiędzy:");
        run.addBreak();
        run.addBreak();
        run.setText(
                "1. " + fullName + ", NIP: " + nip + ", reprezentowane przez " + name + ", zwany dalej Użyczającym, a");
        run.addBreak();
        run.addBreak();
        run.setText("2. " + nazwa + ", nr paszportu " + paszport + " , zam. " + adres
                + " Ukraina, zwany dalej Biorącym do używania.");
        run.addBreak();
        run.setText("mianowitej treści:");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run.setBold(true);
        run.setText("§ 1");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run.setBold(false);
        run.setText("Użyczający oświadcza, iż jest właścicielem lokalu znajdującego się w miejscowości Zawady");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run.setBold(true);
        run.setText("§ 2");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run.setBold(false);
        run.setText("Lokal ten  Użyczający oddaje Biorącemu do bezpłatnego używania.");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run.setBold(true);
        run.setText("§ 3");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run.setBold(false);
        run.setText("1. Biorący do używania potwierdza, iż jest mu znany stan techniczny lokalu. ");
        run.addBreak();
        run.setText(
                "2. Biorący do używania oświadcza, że lokal przekazany do używania jest zdatny do użytku i nie wnosi zastrzeżeń. ");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run.setBold(true);
        run.setText("§ 4");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run.setBold(false);
        run.setText("1. Biorący przyjmuje przedmiot umowy, określony w § 1 do używania, na czas oznaczony  od dnia "
                + start + " do dnia " + koniec + ".");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run.setBold(true);
        run.setText("§ 5");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run.setBold(false);
        run.setText(
                "1. Biorący do używania zobowiązuje się do wykorzystywania lokalu wyłącznie na cele zgodne z jego przeznaczeniem. ");
        run.addBreak();
        run.setText("2. Biorący do używania nie odda przedmiotu umowy do używania i nie podnajmie go osobom trzecim. ");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run.setBold(true);
        run.setText("§ 6");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run.setBold(false);
        run.setText(
                "1. Biorący do używania zobowiązany jest zwrócić przedmiot umowy Użyczającemu, po zakończeniu okresu użyczenia. ");
        run.addBreak();
        run.setText(
                "2. Przedmiot umowy ma zostać zwrócony w stanie nie pogorszonym, bez wezwania ze strony Użyczającego. ");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run.setBold(true);
        run.setText("§ 7");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run.setBold(false);
        run.setText("1. Strony mogą wypowiedzieć niniejszą umowę z zachowaniem trzydniowego terminu wypowiedzenia. ");
        run.addBreak();
        run.setText(
                "2. Użyczający może wypowiedzieć umowę bez zachowania terminu wypowiedzenia, jeśli Biorący do używania:");
        run.addBreak();
        run.setText("a) używa przedmiotu umowy w sposób niezgodny z przeznaczeniem;");
        run.addBreak();
        run.setText("c/ zachowuje się w sposób uciążliwy dla korzystających z innych lokali w budynku");
        run.addBreak();
        run.setText(
                "2. Użyczający może wypowiedzieć umowę bez zachowania terminu wypowiedzenia, jeśli Biorący do używania:");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run.setBold(true);
        run.setText("§ 8");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run.setBold(false);
        run.setText(
                "1. Wszelkie zmiany i uzupełnienia dotyczące niniejszej umowy wymagają formy pisemnej pod rygorem nieważności");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run.setBold(true);
        run.setText("§ 9");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run.setBold(false);
        run.setText("1. Umowa została zawarta w dwóch jednobrzmiących egzemplarzach, po jednym dla każdej ze stron. ");
        run.addBreak();
        run.setText(
                "2. W kwestiach, których nie reguluje niniejsza umowa należy odnieść się do właściwych przepisów Kodeksu Cywilnego.  ");
        run.addBreak();
        run.addBreak();
        run.setText(
                "..............................................                                                                  .................................................");
        run.addBreak();
        run.setText(
                "                Użyczający                                                                                              Biorący w używanie ");

        try {
            document.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        XWPFDocument document2 = new XWPFDocument();
        FileOutputStream out2 = null;
        try {
            out2 = new FileOutputStream(
                    new File(worker.getId() + " - praca.docx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        XWPFTable table = document2.createTable();
        XWPFTableRow tableRowOne = table.getRow(0);

        XWPFParagraph para = document.createParagraph();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        para.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run2 = para.createRun();
        run2.setBold(false);
        run2.setFontSize(11);
        run2.setText("Umowa o pomocy przy zbiorach");
        run2.addBreak();
        run2.setText("Zawarta dnia " + start + " r. w miejscowości Zawady pomiędzy:");
        run2.addBreak();
        run2.setText(
                fullName + ", NIP: " + nip + ", reprezentowane przez " + name + ", zwany dalej rolnikiem, a");
        run2.addBreak();
        run2.setText("a");
        run2.addBreak();
        run2.setText(nazwa);
        run2.addBreak();
        run2.setText("Zam. " + adres + " Ukraina");
        run2.addBreak();
        run2.setText("legitymującym się paszportem seria i nr " + paszport + " zwany dalej pomocnikiem rolnika");
        run2.addBreak();
        run2.setText("§ 1");

        run2.addBreak();
        run2.setText(
                "Na podstawie niniejszej umowy rolnik zleca a  pomocnik rolnika zobowiązuje się do  wykonywania następujących czynności:");
        run2.addBreak();
        run2.setText(
                "Zbiór owoców i warzyw, usuwanie zbędnych części roślin, sortowanie, ważenie, załadunek do chłodni, prace pielęgnacyjne.");
        run2.addBreak();

        run2.setText("§ 2");
        ;
        run2.addBreak();
        run2.setText(
                "1. Za wykonanie prac określonych w § 1 pomocnik rolnika otrzyma po ich wykonaniu wynagrodzenie w wysokości " + wage + " zł/ godz. brutto.");
        run2.addBreak();
        run2.setText("2. Wynagrodzenie płatne jest na podstawie rachunku przedstawionego przez pomocnika rolnika");
        run2.addBreak();

        run2.setText("§ 3");

        run2.addBreak();
        run2.setText("Umowa została zawarta na czas od " + start + " do " + koniec + " r.");
        run2.addBreak();

        run2.setText("§ 4");

        run2.addBreak();
        run2.setText("W sprawach nieuregulowanych niniejszą umową mają zastosowanie przepisy Kodeksu Cywilnego.");
        run2.addBreak();

        run2.setText("§ 5");

        run2.addBreak();
        run2.setText("Umowa została sporządzona w 2 jednobrzmiących egzemplarzach – po 1 dla każdej ze stron.");
        run2.addBreak();

        run2.setText("§ 6");

        run2.addBreak();
        run2.setText(
                "Pomocnik rolnika oświadcza, ze w bieżącym roku świadczył pomoc przy zbiorach łącznie przez ………… dni kalendarzowych");
        run2.addBreak();
        run2.addBreak();
        run2.setText("..............................................");
        run2.addBreak();
        run2.setText("Rolnik ((Фермер фермера)");

        XWPFParagraph para1 = document.createParagraph();
        WorkerServiceImpl.setSingleLineSpacing(paragraph);
        para.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run3 = para1.createRun();
        run3.setBold(false);
        run3.setText("Договор о помощи сборе");

        run3.addBreak();
        run3.setText("Подписано " + start + " г.  в Zawady mежду: ");
        run3.addBreak();
        run3.setText(
                fullName + ", NIP: " + nip + ", reprezentowane przez " + name + ", именуемого далее фермер, a");
        run3.addBreak();
        run3.setText("и ");
        run3.setText(nazwa);
        run3.addBreak();
        run3.setText("прожив " + adres + " Ukraina");
        run3.addBreak();
        run3.setText("паспорт серии и номер " + paszport + " именуемый в дальнейшем помощник фермера");
        run3.addBreak();
        run3.setText("§ 1");

        run3.addBreak();
        run3.setText(
                "В соответствии с этим соглашением, фермер заявляет, а помощник фермера обязуется сделать следующее:");
        run3.addBreak();
        run3.setText(
                "Сбор фруктів і овочів, удаление ненужных частей растений, сортировка, взвешивание, погрузка в холодильник, работа по уходу.");
        run3.addBreak();

        run3.setText("§ 2");
        ;
        run3.addBreak();
        run3.setText(
                "1. За выполнение работы, указанной в § 1, помощник фермера получает вознаграждение в размере " + wage + " злотых в час брутто ");
        run3.addBreak();
        run3.setText("2. Вознаграждение выплачивается на основании счета, представленного помощником фермера");
        run3.addBreak();

        run3.setText("§ 3");

        run3.addBreak();
        run3.setText("Договор подписан на период с " + start + " по " + koniec + " r.");
        run3.addBreak();

        run3.setText("§ 4");

        run3.addBreak();
        run3.setText("В вопросах, не предусмотренных настоящим договором, применяются положения Гражданского кодекса.");
        run3.addBreak();

        run3.setText("§ 5");

        run3.addBreak();
        run3.setText("Договор составлен в 2 одноименных экземплярах  – по  1 для каждой Стороны");
        run3.addBreak();

        run3.setText("§ 6");

        run3.addBreak();
        run3.setText(
                "Помощник фермера заявляет, что в текущем году он оказывал помощь в сборе всего ………… календарных дней");
        run3.addBreak();
        run3.addBreak();
        run3.setText("..............................................");
        run3.addBreak();

        run3.setText("Помощник  (Pomocnik rolnika)");

        tableRowOne.getCell(0).setParagraph(para);
        tableRowOne.addNewTableCell().setParagraph(para1);

        try {
            document2.write(out2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void settlement(Worker worker, String endDate) {

        Date today = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);


        UserDetails userDetails = userDetailsRepository.findByUserId(worker.getUserId());

        String fullName = userDetails.getFullName();
        String nazwa = worker.getName() + " " + worker.getSurname();
        String paszport = worker.getPassport();
        String adres = worker.getAdress();
        String startDate = worker.getStartDate();
        String wage = userDetails.getWage();

        XWPFDocument document = new XWPFDocument();
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(
                    new File(worker.getId() + " - rozliczenie.docx"));
        } catch (FileNotFoundException e) {
            System.out.println("Nieodpowiednia ścieżka dla pliku");
            e.printStackTrace();
        }

        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setFontSize(12);
        run.setText("Rozliczenie");
        run.addBreak();
        run.addBreak();
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run.setBold(false);
        run.setText("Ja " + nazwa + ", nr paszportu " + paszport + " , zam. " + adres + " Ukraina przepracowałem/am w: "
                + fullName + " od dnia " + startDate + " do dnia " + endDate + " ........... godzin.");
        run.addBreak();
        run.addBreak();
        run.addBreak();
        run.setText("............. x " + wage + " zł = ...............");
        run.addBreak();
        run.addBreak();
        run.addBreak();
        run.setText("Kwotę należną otrzymałem/am " + endDate + ".");
        run.addBreak();
        run.addBreak();
        run.addBreak();
        run.addBreak();
        run.addBreak();
        run.addBreak();
        run.setText(
                "       Podpis pomocnika sezonowego                                                                Podpis wypłacającego      ");

        try {
            document.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
