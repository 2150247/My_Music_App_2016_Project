package pt.ipleiria.pt.mymusicapp2016;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.service.notification.NotificationListenerService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";


    /*
    * Public -> A variavel pode ser acessidade a partir de qualquer class
    * Static -> Não é preciso criar uma "ligação especial para a chamar", pois ao ser Static torna-se global e pode-se aceder por qualquer class etc
    * Final -> Significa que não muda de valor!
    * Class -> Refere-se sempre a um objeto!
    * Extends-> "Exterção"; Vai Buscar neste caso a "AppCompatActivity" (Barra Superior do Programa "MyMusicApp2016")!
    * AppCompatActivity->PAI || MainActivity-> FILHO
    * O Super-> Chama a função da class pai, "Contraria o Override"!
    */



    private ArrayList<String> listaMusicas;
    private ArrayList<String> linkYoutube;



    @Override //Isto permite rodar no programa as coisas que eu quiser que ele rode!
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  //Isto vai definir o Layout a apresentar! Se esta desaparecer, não haverá layout!

        Log.e(TAG, "Estou aqui -> onCreate()");  // Isto fará com que apareca uma mensagem de quando houver erro, na consola!

        //Ex: editTextSearch =  (EditText)findViewById(R.id.editText_text); //Isto fará com que haja confiança no tipo da variável, se não esta vai dar erro e ficar desconfiada! || Isto busca o id que define no layout.//
        //                        ^                                                   |
        //                        |                                                   |
        //                        |___________________________________________________|






        //->Funcionalidade Nova:Persistir Dados ->Continuação*
        //__________________________________________________________________________________________

        SharedPreferences sp = getSharedPreferences("appAlbuns",0);
        Set<String> albumSet = sp.getStringSet("albumKey", new HashSet<String>());

        Log.e(TAG, "Estou aqui -> onCreate->Persistir Dados()");
        //__________________________________________________________________________________________





        listaMusicas = new ArrayList<String>(albumSet);

        //Isto agora não interessa, pois já é possivel guardar os dados com a persistencia de dados...
        /*listaMusicas.add("Avenged Sevenfold ❂ Hail to the King ❂ 2013 ❂ ✭ 5 Rating ✭");
        listaMusicas.add("Limp Bizkit ❂ Significant Other ❂ 1999 ❂ ✭ 4 Rating ✭");
        listaMusicas.add("Linkin Park ❂ Meteora ❂ 2003 ❂ ✭ 4 Rating ✭");
        listaMusicas.add("Seather ❂ Disclaimer II ❂ 2004 ❂ ✭ 3 Rating ✭");
        listaMusicas.add("Korn ❂ Issues ❂ 1999 ❂ ✭ 4 Rating ✭");*/


        //________________________________________________________
        //Nova Funcionalidade-> Após ter definido o novo layout e em baixo o ter chamado...Fas-se p seguinte:
        SimpleAdapter adapter = createSimpleAdapter(listaMusicas);

                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                //android.R.layout.simple_list_item_1, listaMusicas);
        //->Deixou de ser necessário após o novo layout
        //_________________________________________________________


        ListView listView = (ListView) findViewById(R.id.listviewmusics); //R é a class das constantes.
        listView.setAdapter(adapter);



        //Fazer cada musica ser clicavel com ligação ao youtube:

        linkYoutube = new ArrayList<String>();

        /*linkYoutube.add("https://www.youtube.com/watch?v=bT8FEOJEFcI&list=PLzOQr4GdVhWs0D8RA6uHlVWXtAJnw6k7y");
        linkYoutube.add("https://www.youtube.com/watch?v=ZpUYjpKg9KY&index=4&list=PLsMILDNKDKSE5LVmUS2xeod9Q9JAJI4u5");
        linkYoutube.add("https://www.youtube.com/watch?v=U6R-twDkrcI&list=PLBiPNxqFKPZJAC_So1nqBa_TEYCBkanZL");
        linkYoutube.add("https://www.youtube.com/watch?v=mF53On_P7qI&list=PLVMCuf3Y8oLD3hnzB_coBQUI2ktTaTGnv");
        linkYoutube.add("https://www.youtube.com/watch?v=2s3iGpDqQpQ&list=PLDgiaVwE3PvFLE6Hcqb7P18GxVOc8NRer&index=2");*/



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Toast.makeText(getBaseContext(),"Music"+position, Toast.LENGTH_LONG).show();
                String video = linkYoutube.get(position);
                //Toast.makeText(getBaseContext(),"Link Youtube: "+video, Toast.LENGTH_LONG).show();

                try{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(video)));
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, R.string.link, Toast.LENGTH_SHORT).show();
                }

            }
        });




        Spinner spinnerType = (Spinner) findViewById(R.id.spinner_search);
        //adapter2 = ArrayAdapter.createFromResource(getBaseContext(), R.array.options_spinner, android.R.layout.simple_spinner_item);
        // |--Isto é a forma de gerir o Vetor (Array) e a view!                                  |- Chamar o vetor;               |- Ir buscar um layout default (existente no android studio) para aplicar o vetor!

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.options_spinner, android.R.layout.simple_spinner_item);


        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter2);
        // Isto faz a gestão do que vai apresentar ao utilizador!




        //->Nova Funcionalidade:Apagar Álbum
        //Vou usar o LongClick para ser possível apagar os mesmos...
        //___________________________________________________________________________________________________________________________________

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                //Código que é executado quando se clica num item da listviewmusics

                Toast.makeText(MainActivity.this, "Apagou o Álbum!", Toast.LENGTH_SHORT).show();


                //O que vai permitir apagar...
                listaMusicas.remove(position);

                //Atualizar de novo a lista de álbuns...
                //ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,listaMusicas);
                SimpleAdapter adapter = createSimpleAdapter(listaMusicas);

                ListView listView = (ListView) findViewById(R.id.listviewmusics);
                listView.setAdapter(adapter);

                return true;

            }
        });

                Log.e(TAG, "Estou aqui -> Apagar Álbuns()");
        //____________________________________________________________________________________________________________________________________
    }




    //-->Nova funcionalidade: Novo método após as modificações onStop e onCreate....Isto é para usar o novo layout com cliparts
    //_______________________________________________________________________________________________

    private SimpleAdapter createSimpleAdapter(ArrayList<String> listaMusicas) {
        List<HashMap<String, String>> simpleAdapterData = new ArrayList<HashMap<String, String>>();

        for (String c : listaMusicas) {
            HashMap<String, String> hashMap = new HashMap<>();

            String[] split = c.split(" \\❂ ");
            Log.e(TAG, ""+c+" "+split.length);
            hashMap.put("artist", split[0]);
            hashMap.put("album", split[1]);
            hashMap.put("year", split[2]);
            hashMap.put("editora", split[3]);
            hashMap.put("pontuacao_rating", split[4]);


            simpleAdapterData.add(hashMap);
        }

        String[] from = {"artist", "album", "year", "editora","pontuacao_rating"};
        int[] to = {R.id.textView_artista, R.id.textView_album, R.id.textView_ano, R.id.textView_editora, R.id.textView_rating};
        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), simpleAdapterData, R.layout.listview_albuns, from, to);
        return simpleAdapter;
    }
    //_________________________________________________________________________________________________






        //->Nova Funcionalidade:Persistir Dados
    //________________________________________________________________________________________________________________
        //Criar o método onStop
   @Override
   protected void onStop(){
        super.onStop();

        Toast.makeText(MainActivity.this, "A guardar dados...", Toast.LENGTH_SHORT).show();


          //Usando o Share Preferences vai se então fazer ser possível guardar os álbuns...
        SharedPreferences sp= getSharedPreferences("appAlbuns",0); //A shared vai ser indentificada por um nome ! Neste ex: "appAlbuns", O "0" é o modo privado, fica la tudo guardado.


          //Agora crio um editor que vai guardar as preferências
        SharedPreferences.Editor edit = sp.edit();

          // Irá fazer com que dê para guardar os albuns
        HashSet albumSet=new HashSet (listaMusicas);

          //Aqui vai ser pedido a chave(Dada por mim) e os valores (Dados Albuns)...
        edit.putStringSet("albumKey"/*Chave*/, albumSet /*Valor*/);

        //ENVIAR ESTE CONTEÚDO PARA O COMMIT!!
        edit.commit();

        Log.e(TAG, "Estou aqui -> onStop-Persistir Dados()");
    }     // Agora para ir buscar o conteúdo à memória, faço o resto no onCreate...Continua*

     //_________________________________________________________________________________________________________________





    public void pesquisar(View view) {

        EditText text = (EditText) findViewById(R.id.editText_text);
        Spinner sp = (Spinner) findViewById(R.id.spinner_search);
        ListView lv = (ListView) findViewById(R.id.listviewmusics);

        int PosicaoItem=sp.getSelectedItemPosition(); //Dá a posição do que está selecionado "All..Artist etc"

        ArrayList<String> search_music = new ArrayList<String>();
        String termo = text.getText().toString();



        if (termo.equals("")) {
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                   // android.R.layout.simple_list_item_1, listaMusicas);
            SimpleAdapter adapter = createSimpleAdapter(listaMusicas);
            lv.setAdapter(adapter);
            Toast.makeText(MainActivity.this, R.string.showing, Toast.LENGTH_SHORT).show();
        } else {
            if (PosicaoItem==0) {
                for (String c : listaMusicas) {
                    if ((c.toLowerCase().contains(termo.toLowerCase()))){
                        search_music.add(c);
                    }
                }
            } else if (PosicaoItem==1) {
                for (String c : listaMusicas) {
                    String[] s = c.split("\\ ❂");
                    String art= s[0];
                    if ((art.toLowerCase().contains(termo.toLowerCase()))){
                        search_music.add(c);
                    }
                }
            } else if (PosicaoItem==2) {
                for (String c : listaMusicas) {
                    String[] s = c.split("\\ ❂");
                    String alb = s[1];
                    if ((alb.toLowerCase().contains(termo.toLowerCase()))) {
                        search_music.add(c);
                    }
                }
            } else if (PosicaoItem==3) {
                for (String c : listaMusicas) {
                    String[] s = c.split("\\ ❂");
                    String yr = s[2];
                    if ((yr.toLowerCase().contains(termo.toLowerCase()))) {
                        search_music.add(c);
                    }
                }
            }else if (PosicaoItem==4) {
                for (String c : listaMusicas) {
                    String[] s = c.split("\\ ❂");
                    String rt = s[3];
                    if ((rt.toLowerCase().contains(termo.toLowerCase()))) {
                        search_music.add(c);
                    }
                }
            }else if (PosicaoItem==5) {
                for (String c : listaMusicas) {
                    String[] s = c.split("\\ ❂");
                    String rt = s[4];
                    if ((rt.toLowerCase().contains(termo.toLowerCase()))) {
                        search_music.add(c);
                    }
                }
            }
            boolean vazia = search_music.isEmpty();


            if (!vazia) {
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                       //android.R.layout.simple_list_item_1, search_music);
                SimpleAdapter adapter = createSimpleAdapter(search_music);
                lv.setAdapter(adapter);
                Toast.makeText(MainActivity.this, R.string.showing, Toast.LENGTH_SHORT).show();
            } else {
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        //android.R.layout.simple_list_item_1, listaMusicas);
                SimpleAdapter adapter = createSimpleAdapter(listaMusicas);
                lv.setAdapter(adapter);
                Toast.makeText(MainActivity.this, R.string.noresults, Toast.LENGTH_SHORT).show();
            }

        }


        Log.e(TAG, "Estou aqui -> pesquisar()");
    }

   //Cria o alerta para adicionar as novas musicas;

    public void addAlbum(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View layoutAdd = inflater.inflate(R.layout.adicionar, null);
        builder.setView(layoutAdd);

        // Adiciona os botões
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AlertDialog al = (AlertDialog) dialog;

                EditText Artist = (EditText)al.findViewById(R.id.editText_artista);
                EditText Album = (EditText)al.findViewById(R.id.editText_album);
                EditText Year = (EditText)al.findViewById(R.id.editText_year);
                EditText Publisher = (EditText)al.findViewById(R.id.editText_editora);
                RatingBar Pontuacao = (RatingBar)al.findViewById(R.id.ratingBar);
                EditText Play = (EditText)al.findViewById(R.id.editText_play);


                String artist = Artist.getText().toString();
                String album = Album.getText().toString();
                String year = Year.getText().toString();
                String editora = Publisher.getText().toString();
                int pontuacao_rating = (int)Pontuacao.getRating();  //float pontuacao_rating = Pontuacao.getRating()->Assim era decimal!
                String video = Play.getText().toString();


                   //.........
                  //  rating=1.0 = "✭"  rating=2.0 = "✭✭"   rating=3.0 = "✭✭✭"  rating=4.0 = "✭✭✭✭"  rating=5.0 = "✭✭✭✭✭✭"


                //Validações
                if(artist.length() == 0 || album.length() == 0 || year.length() == 0 || editora.length() == 0 || video.length() == 0){
                    Toast.makeText(MainActivity.this, R.string.inform, Toast.LENGTH_SHORT).show();
                    return;
                }


                    String newMusic = artist + " ❂ " + album + " ❂ " + year + " ❂ " + editora + " ❂ " +  " ✭ " + pontuacao_rating + " Rating ✭ ";




                listaMusicas.add(newMusic);
                linkYoutube.add(video);

                ListView lv = (ListView) findViewById(R.id.listviewmusics);
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, listaMusicas);
                SimpleAdapter adapter = createSimpleAdapter(listaMusicas);
                lv.setAdapter(adapter);



                Toast.makeText(MainActivity.this, R.string.newsongadd, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(MainActivity.this, R.string.cancelled, Toast.LENGTH_SHORT).show();

            }



        });
        //Titulo+Messagem (Tela Add Album)
        builder.setTitle(R.string.new_song);
        builder.setMessage(R.string.new_datamusic);
        // Criar o AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

        Log.e(TAG, "Estou aqui -> addAlbum()");
    }

}