#!/bin/bash

# List of data (replace with your desired list)
data=(
  '{"cliente":"Joao","cpf":"00000000000","cep":"54518280"}'
  '{"cliente":"Joao","cpf":"10000000000","cep":"64059580"}'
  '{"cliente":"Joao","cpf":"20000000000","cep":"79063190"}'
  '{"cliente":"Joao","cpf":"30000000000","cep":"71266100"}'
  '{"cliente":"Joao","cpf":"40000000000","cep":"59052425"}'
  '{"cliente":"Joao","cpf":"50000000000","cep":"58026080"}'
  '{"cliente":"Joao","cpf":"60000000000","cep":"78720841"}'
  '{"cliente":"Joao","cpf":"70000000000","cep":"69909390"}'
  '{"cliente":"Joao","cpf":"80000000000","cep":"58406545"}'
  '{"cliente":"Joao","cpf":"90000000000","cep":"40471015"}'
  '{"cliente":"Joao","cpf":"10000000000","cep":"35430270"}'
  '{"cliente":"Joao","cpf":"11000000000","cep":"96200020"}'
  '{"cliente":"Joao","cpf":"12000000000","cep":"60721160"}'
  '{"cliente":"Joao","cpf":"13000000000","cep":"49042780"}'
  '{"cliente":"Joao","cpf":"14000000000","cep":"69318155"}'
  '{"cliente":"Joao","cpf":"15000000000","cep":"59035110"}'
  '{"cliente":"Joao","cpf":"16000000000","cep":"88318500"}'
)

# Function to process each element with a random sleep time
process_data() {
  item=$1
  # Generate a random sleep time between 5 and 30 seconds
  sleep_time=$((RANDOM % 26 + 5))

  # Process the data (replace this with your desired operation)
  echo "Processing data: $item"
  curl -X POST -H "Content-Type: application/json" -d "$item" http://localhost:8080/api/v1/contratacoes
  # Add your logic here to work with the current data item

  # Sleep for the randomly generated time
  echo "Sleeping for $sleep_time seconds..."
  sleep "$sleep_time"
}

# Shuffle the list of data to randomize the order
shuffled_data=($(shuf -e "${data[@]}"))

# Iterate over the shuffled list and process each data item
for item in "${shuffled_data[@]}"; do
  process_data "$item"
done