package com.flab.fkream.AutoComplete;

import com.flab.fkream.item.Item;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
public class Trie {

    private TrieNode root = new TrieNode();

    public void insert(Item item) {
        String word = item.getItemName();
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            c = Character.toLowerCase(c);
            if (!node.getChildren().containsKey(c)) {
                TrieNode trieNode = new TrieNode();
                node.getChildren().put(c, trieNode);
            }
            node = node.getChildren().get(c);
        }
        node.setEnd(true);
        if (node.isEnd()) {
            node.setItem(item);
        }
    }

    public List<Item> search(String word) {
        List<Item> results = new ArrayList<>();
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            c = Character.toLowerCase(c);
            if (!node.getChildren().containsKey(c)) {
                return results;
            }
            node = node.getChildren().get(c);
        }
        findAllWords(node, word, results);
        return results;
    }

    private void findAllWords(TrieNode node, String prefix, List<Item> result) {
        if (node.isEnd()) {
            result.add(node.getItem());
        }
        for (Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            char ch = entry.getKey();
            TrieNode child = entry.getValue();
            findAllWords(child, prefix + ch, result);
        }
    }
}
