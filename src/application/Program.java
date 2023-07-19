package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.entities.Product;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		List<Product> list = new ArrayList<>();
		
		System.out.println("Enter file path: ");
		String sourceFileStr = sc.nextLine();
		
		File sourceFile = new File(sourceFileStr);
		
		String sourceFolderStr = sourceFile.getParent(); //pega somente o caminho desprezando o nome do arquivo
		System.out.println(sourceFolderStr);
		
		boolean success = new File(sourceFolderStr + "\\out").mkdir(); //cria uma pasta c o diretorio de final \\out
		System.out.println("Folder created: " + success);
		
		String targetFileStr = sourceFolderStr + "\\out\\summary.csv";
		
		try(BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))){ //buffered reader instanciado a partir de um file reader
			
			String itemCsv = br.readLine(); //enquanto eu n chegar no final do arquivo quero obter os dados
			while(itemCsv!=null) {
				
				String[] fields = itemCsv.split(","); //split, eu quebro a linha em 3 valores onde acho a virgula
				String name = fields[0];
				double price = Double.parseDouble(fields[1]); //devo dar um parse pra converter pra double!
				int quantity = Integer.parseInt(fields[2]);
				
				list.add(new Product(name, price, quantity)); //vou adicionando na lista
				
				itemCsv = br.readLine();
				
			}
			
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))){ //nao coloco true pq o arquivo n existe
				
				for(Product item : list) {
					bw.write(item.getName() + "," + String.format("%.2f", item.total()));
					bw.newLine(); //cria mais uma linha ao arquivo
				}
				System.out.println(targetFileStr + " CREATED!");
			}
			
			catch(IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
			
		}
		
		catch(IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		
		sc.close();
		
	}
	
}
