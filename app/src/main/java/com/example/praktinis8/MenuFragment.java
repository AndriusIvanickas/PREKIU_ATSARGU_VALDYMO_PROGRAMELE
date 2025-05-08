package com.example.praktinis8;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuFragment extends Fragment {

    private List<ItemEntity> dishes;
    private ItemAdapter adapter;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri = null;
    private ImageView imagePreviewGlobal;

    private ActivityResultLauncher<Intent> pickImageLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dishes = ItemRepository.getItems();
        adapter = new ItemAdapter(dishes);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.addItemFab);
        fab.setOnClickListener(v -> showAddItemDialog());

        adapter.setOnItemClickListener(item -> showEditItemDialog(item));
        adapter.setOnItemLongClickListener(item -> showSellItemDialog(item));

        setupPickImageLauncher();

        return view;
    }

    private void setupPickImageLauncher() {
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            requireContext().getContentResolver().takePersistableUriPermission(
                                    selectedImageUri,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                            );
                            if (imagePreviewGlobal != null) {
                                Picasso.get().load(selectedImageUri).into(imagePreviewGlobal);
                            }
                        }
                    }
                }
        );
    }

    private void showAddItemDialog() {
        selectedImageUri = null;
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_item, null);

        imagePreviewGlobal = dialogView.findViewById(R.id.imagePreview);
        imagePreviewGlobal.setOnClickListener(v -> pickImageFromGallery());

        new AlertDialog.Builder(requireContext())
                .setTitle("Pridėti prekę")
                .setView(dialogView)
                .setPositiveButton("Pridėti", (dialog, which) -> {
                    try {
                        String name = ((EditText) dialogView.findViewById(R.id.inputName)).getText().toString();
                        String description = ((EditText) dialogView.findViewById(R.id.inputDescription)).getText().toString();
                        String price = ((EditText) dialogView.findViewById(R.id.inputPrice)).getText().toString();
                        int quantity = Integer.parseInt(((EditText) dialogView.findViewById(R.id.inputQuantity)).getText().toString());
                        double costPrice = Double.parseDouble(((EditText) dialogView.findViewById(R.id.inputCostPrice)).getText().toString());
                        boolean seasonal = ((CheckBox) dialogView.findViewById(R.id.inputSeasonal)).isChecked();
                        int minQuantity = Integer.parseInt(((EditText) dialogView.findViewById(R.id.inputMinQuantity)).getText().toString());

                        ItemEntity newItem = new ItemEntity();
                        newItem.name = name;
                        newItem.description = description;
                        newItem.price = price;
                        newItem.quantity = quantity;
                        newItem.costPrice = costPrice;
                        newItem.seasonal = seasonal;
                        newItem.minQuantity = minQuantity;
                        newItem.imageUri = (selectedImageUri != null) ? selectedImageUri.toString() : "";

                        ItemRepository.addItem(newItem);
                        dishes.add(newItem);
                        adapter.notifyItemInserted(dishes.size() - 1);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .setNegativeButton("Atšaukti", null)
                .create()
                .show();
    }

    private void showEditItemDialog(ItemEntity item) {
        selectedImageUri = (item.imageUri != null && !item.imageUri.isEmpty()) ? Uri.parse(item.imageUri) : null;
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_item, null);

        imagePreviewGlobal = dialogView.findViewById(R.id.imagePreview);
        if (selectedImageUri != null) {
            Picasso.get().load(selectedImageUri).into(imagePreviewGlobal);
        }
        imagePreviewGlobal.setOnClickListener(v -> pickImageFromGallery());

        ((EditText) dialogView.findViewById(R.id.inputName)).setText(item.name);
        ((EditText) dialogView.findViewById(R.id.inputDescription)).setText(item.description);
        ((EditText) dialogView.findViewById(R.id.inputPrice)).setText(item.price);
        ((EditText) dialogView.findViewById(R.id.inputQuantity)).setText(String.valueOf(item.quantity));
        ((EditText) dialogView.findViewById(R.id.inputCostPrice)).setText(String.valueOf(item.costPrice));
        ((CheckBox) dialogView.findViewById(R.id.inputSeasonal)).setChecked(item.seasonal);
        ((EditText) dialogView.findViewById(R.id.inputMinQuantity)).setText(String.valueOf(item.minQuantity));

        new AlertDialog.Builder(requireContext())
                .setTitle("Redaguoti prekę")
                .setView(dialogView)
                .setPositiveButton("Išsaugoti", (dialog, which) -> {
                    try {
                        item.name = ((EditText) dialogView.findViewById(R.id.inputName)).getText().toString();
                        item.description = ((EditText) dialogView.findViewById(R.id.inputDescription)).getText().toString();
                        item.price = ((EditText) dialogView.findViewById(R.id.inputPrice)).getText().toString();
                        item.quantity = Integer.parseInt(((EditText) dialogView.findViewById(R.id.inputQuantity)).getText().toString());
                        item.costPrice = Double.parseDouble(((EditText) dialogView.findViewById(R.id.inputCostPrice)).getText().toString());
                        item.seasonal = ((CheckBox) dialogView.findViewById(R.id.inputSeasonal)).isChecked();
                        item.minQuantity = Integer.parseInt(((EditText) dialogView.findViewById(R.id.inputMinQuantity)).getText().toString());
                        item.imageUri = (selectedImageUri != null) ? selectedImageUri.toString() : item.imageUri;

                        ItemRepository.updateItem(item);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .setNegativeButton("Atšaukti", null)
                .setNeutralButton("Ištrinti", (dialog, which) -> {
                    ItemRepository.deleteItem(item);
                    List<ItemEntity> updatedItems = ItemRepository.getItems();
                    adapter.updateItems(updatedItems);
                    dishes.remove(item);
                    adapter.notifyDataSetChanged();
                })
                .create()
                .show();
    }

    private void showSellItemDialog(ItemEntity item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Parduoti: " + item.name);

        final EditText input = new EditText(requireContext());
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Parduoti", (dialog, which) -> {
            try {
                int sellQuantity = Integer.parseInt(input.getText().toString());
                if (sellQuantity <= 0 || sellQuantity > item.quantity) {
                    return;
                }


                ItemEntity updatedItem = new ItemEntity();
                updatedItem.id = item.id;
                updatedItem.name = item.name;
                updatedItem.description = item.description;
                updatedItem.price = item.price;
                updatedItem.costPrice = item.costPrice;
                updatedItem.minQuantity = item.minQuantity;
                updatedItem.seasonal = item.seasonal;
                updatedItem.imageUri = item.imageUri;
                updatedItem.quantity = item.quantity - sellQuantity;


                ItemRepository.updateItem(updatedItem);


                SalesRecordEntity sale = new SalesRecordEntity();
                sale.itemName = item.name;
                sale.quantitySold = sellQuantity;
                sale.timestamp = System.currentTimeMillis();
                ItemRepository.recordSale(sale);

                List<ItemEntity> refreshed = ItemRepository.getItems();
                adapter.updateItems(refreshed);


                List<ItemEntity> updatedItems = ItemRepository.getItems();
                adapter.updateItems(updatedItems);
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    ((MainActivity) requireActivity()).refreshFragments();
                }, 300);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        builder.setNegativeButton("Atšaukti", null);
        builder.show();
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        pickImageLauncher.launch(intent);
    }
}
