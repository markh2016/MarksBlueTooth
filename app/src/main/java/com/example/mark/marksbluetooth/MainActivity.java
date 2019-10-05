package com.example.mark.marksbluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    //widgets
    Button btnPaired;
    Button BlueToothOff ;
    Button BlueToothOn ;
    ListView devicelist;
    //Bluetooth
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    public static String EXTRA_ADDRESS = "device_address";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Calling widgets
        btnPaired = (Button)findViewById(R.id.button);
        devicelist = (ListView)findViewById(R.id.listView);
        BlueToothOff =(Button)findViewById(R.id.butBlueOff);
        BlueToothOn =(Button)findViewById(R.id.butBlueOn);


        //if the device has bluetooth
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if(myBluetooth == null)
        {
            //Show a mensag. that the device has no bluetooth adapter
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();

            //finish apk
            finish();
        }

        BlueToothOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myBluetooth.isEnabled())
                {
                    //Ask to the user turn the bluetooth on
                    Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnBTon,1);
                }
            }
        });

        BlueToothOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myBluetooth.isEnabled())
                {
                    myBluetooth.disable();
                    Toast.makeText(getApplicationContext(),"Bluetooth turned off",
                            Toast.LENGTH_LONG).show();
                }


            }
        });


        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                pairedDevicesList();
            }
        });
    } // on create

    private void pairedDevicesList()
    {
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size()>0)
        {
            for(BluetoothDevice bt : pairedDevices)
            {
                list.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.select_dialog_item, list);
        devicelist.setAdapter(adapter);



        AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener()
        {
            public void onItemClick (AdapterView<?> av, View v, int arg2, long arg3)
            {
                // Get the device MAC address, the last 17 chars in the View
                String info = ((TextView) v).getText().toString();
                String address = info.substring(info.length() - 17);
                Toast.makeText(getApplicationContext(), address, Toast.LENGTH_LONG).show();


                //Make an intent to start next activity.
                Intent i = new Intent(MainActivity.this, ledControl.class);


                i.putExtra(EXTRA_ADDRESS, address); //this will be received at ledControl (class) Activity
                startActivity(i);
            }
        };

        devicelist.setOnItemClickListener(myListClickListener);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_device_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
